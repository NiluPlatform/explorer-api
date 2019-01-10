package tech.nilu.platform.explorer.web3.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.AddressRepository;
import tech.nilu.platform.explorer.model.Address;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.model.Transaction;
import tech.nilu.platform.explorer.web3.contracts.ERC20Basic;
import tech.nilu.platform.explorer.web3.contracts.ERC20BasicImpl;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mariameda on 2/11/18.
 */
@Component("defaultAddressHandler")
public class DefaultAddressHandler implements AddressHandler {

    private static Log log = LogFactory.getLog(DefaultAddressHandler.class);


    @Autowired
    private Web3j web3j;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Mono<Tuple2<Address, Address>> onMeetInTransaction(Transaction t) {
        CompletableFuture<Tuple2<Address, Address>> ret = new CompletableFuture<>();
        Mono.zip(handleAddress(t.getFrom(), t)
                , handleAddress(t.getTo(), t)).subscribe(
                result -> {
                    Address from = result.getT1();
                    Address to = result.getT2();
                    if (Boolean.TRUE.equals(to.getContract()) && (!from.getActiveContracts().contains(to))) {
                        log.debug("add contract address: " + to.getAddress() + " to " + from.getAddress()
                                + "on transaction#" + t.getTransactionHash() + " on block#" + t.getBlockNumber());
                        from.getActiveContracts().add(to);
                        addressRepository.save(from).subscribe();
                    }
                    if (Boolean.TRUE.equals(from.getContract()) && (!to.getActiveContracts().contains(from))) {
                        log.debug("add contract address: " + from.getAddress() + " to " + to.getAddress()
                                + "on transaction#" + t.getTransactionHash() + " on block#" + t.getBlockNumber());
                        to.getActiveContracts().add(from);
                        addressRepository.save(to).subscribe();
                    }
                    ret.complete(new Tuple2<>(from, to));
                });
        return Mono.fromFuture(ret);
    }

    @Override
    public Mono<Address> handleMinerAddress(String address, Block block) {
        log.debug("handle miner address: " + address
                + " on block#" + block.getNumber());
        /*return addressRepository.findById(address)
                .switchIfEmpty(createAddress(address, block.getNumber()));
                */
        CompletableFuture<Address> ret = new CompletableFuture<>();
        addressRepository.findById(address)
                .switchIfEmpty(Mono.just(new Address()))
                .subscribe(a -> {
                    if (a.getAddress() == null ||
                            a.getContract() == null)
                        createAddress(address, block.getNumber().longValue(), block.getTimestamp()).subscribe(
                                s -> ret.complete(s)
                                , e -> ret.completeExceptionally(e));
                    else
                        ret.complete(a);
                });
        return Mono.fromFuture(ret);
    }

    @Override
    public Mono<Address> handleDeployedContractAddress(String contractAddress, Transaction t) {
        CompletableFuture<Address> ret = new CompletableFuture<>();
        Mono.zip(handleAddress(t.getFrom(), t)
                , handleAddress(contractAddress, t)).subscribe(
                result -> {
                    Address from = result.getT1();
                    Address contract = result.getT2();
                    contract.setDeployer(from.getAddress());
                    addressRepository.save(contract).subscribe();
                    if (Boolean.TRUE.equals(contract.getContract()) && (!from.getActiveContracts().contains(contract))) {
                        log.debug("add deployed contract address: " + contractAddress + " to " + from.getAddress()
                                + "on transaction#" + t.getTransactionHash() + " on block#" + t.getBlockNumber());
                        from.getActiveContracts().add(contract);
                        addressRepository.save(from).subscribe();
                    }
                    ret.complete(contract);
                });
        return Mono.fromFuture(ret);
    }

    private Mono<Address> handleAddress(String address, Transaction transaction) {
        log.debug("handle address: " + address + " on transaction#" + transaction.getTransactionHash()
                + " on block#" + transaction.getBlockNumber());
        CompletableFuture<Address> ret = new CompletableFuture<>();
        if (address == null)
            ret.complete(new Address());
        else
            addressRepository.findById(address)
                    .switchIfEmpty(Mono.just(new Address()))
                    .subscribe(a -> {
                        if (a.getAddress() == null ||
                                a.getContract() == null)
                            createAddress(address
                                    , transaction.getBlockNumber()
                                    , transaction.getTimestamp()).subscribe(
                                    s -> ret.complete(s)
                                    , e -> ret.completeExceptionally(e));
                        else
                            ret.complete(a);
                    });
        return Mono.fromFuture(ret);
    }

    private Mono<Address> createAddress(String address, long b, long timestamp) {
        log.debug("check for code of new address: " + address + " on block#" + b);
        // return Mono.defer(() -> {
        CompletableFuture<Address> ret = new CompletableFuture<>();
        web3j.ethGetCode(address, DefaultBlockParameterName.LATEST)
                .sendAsync().whenCompleteAsync((c, e) -> {
            Address model = new Address(address);
            model.setTimestamp(timestamp);
            model.setContract(c == null ? null : c.getCode() != null && !c.getCode().equals("0x"));
            if (e != null) {
                log.error("exception in get code of address#" + address,
                        new RuntimeException("exception in get code of address#" + address, e));
            }
            log.debug("new address: " + address + " with code "
                    + (c == null ? "null" : c.getCode())
                    + " on block#" + b);
            if (Boolean.TRUE.equals(model.getContract())) {
                TransactionManager tm = new ReadonlyTransactionManager(web3j, address);
                ERC20Basic basic = ERC20BasicImpl.load(address, web3j, tm, BigInteger.ZERO, BigInteger.ZERO);
                Mono.zip(Mono.fromFuture(basic.name().sendAsync())
                        , Mono.fromFuture(basic.symbol().sendAsync())).doOnError(
                        th -> {
                            log.debug("new contract: " + address + " on block#" + b);
                            model.setErc20support(false);
                            addressRepository.save(model).subscribe(ret::complete);
                        }
                ).subscribe(tuple -> {
                    log.debug("new erc20(" + tuple.getT2() + "): " + address + " on block#" + b);
                    model.setErc20support(true);
                    model.setName(tuple.getT1());
                    model.setSymbol(tuple.getT2());
                    addressRepository.save(model).subscribe(ret::complete);
                });
            } else {
                log.debug("new personal address: " + address + " on block#" + b);
                addressRepository.save(model).subscribe(ret::complete);
            }

        });
        return Mono.fromFuture(ret);
        //});
    }
}
