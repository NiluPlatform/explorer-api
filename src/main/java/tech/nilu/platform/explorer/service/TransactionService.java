package tech.nilu.platform.explorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.BlockRepository;
import tech.nilu.platform.explorer.dao.ERC20TransferRepository;
import tech.nilu.platform.explorer.dao.TransactionsRepository;
import tech.nilu.platform.explorer.dto.TransactionDetail;
import tech.nilu.platform.explorer.model.ERC20Transfer;
import tech.nilu.platform.explorer.model.Transaction;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mariameda on 2/10/18.
 */
@Service
public class TransactionService {

    //@Autowired
    //private MongoTemplate mongoTemplate;

    private static Sort TX_DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "blockNumber", "transactionIndex");

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private ERC20TransferRepository erc20TransferRepository;

    @Autowired
    private BlockRepository blockRepository;


    public Mono<BigInteger> loadLastBlockNumber() {
       /* return Mono.fromCallable( () -> {
            MapReduceResults<DBObject> mr = mongoTemplate.mapReduce("transactions", "function map() {\n" +
                    "\temit( 1, {\n" +
                    "\t\tmax: this.blockNumber" +
                    "\t});\n" +
                    "}\n", "function reduce(key, values) {\n" +
                    "\treturn values.reduce(function reduce(previous, current, index, array) {\n" +
                    "\t\treturn {\n" +
                    "\t       max: Math.max(current.max, index == 0 ? 0 : previous.max )\n" +
                    "\t\t};\n" +
                    "\t})\n" +
                    "}", DBObject.class);

        });*/

        return transactionsRepository
                .findAll(PageRequest.of(0, 1, TX_DEFAULT_SORT))
                .switchIfEmpty(Mono.just(new Transaction()))
                .map(t -> t.getBlockNumber() == 0 ? BigInteger.ZERO : BigInteger.valueOf(t.getBlockNumber()))
                .next();
    }

    public Mono<Transaction> save(Transaction transaction) {
        return transactionsRepository.insert(transaction);
    }

    public Flux<Transaction> loadTransactions(int page
            , int len) {
        Flux<Transaction> result = transactionsRepository.findAll(
                PageRequest.of(page, len, TX_DEFAULT_SORT)
        );
        return result;
    }


    public Mono<TransactionDetail> lookupByHash(String hash) {
        CompletableFuture<TransactionDetail> ret = new CompletableFuture<>();
        Mono.zip(transactionsRepository.findByTransactionHash(Mono.just(hash))
                        .switchIfEmpty(Mono.just(new Transaction()))
                , erc20TransferRepository.findByTransactionHash(Mono.just(hash))
                        .switchIfEmpty(Mono.just(new ERC20Transfer()))).subscribe(
                tuple -> {
                    if (tuple.getT1().getTransactionHash() != null) {
                        ret.complete(new TransactionDetail(tuple.getT1()
                                , tuple.getT2().getTransactionHash() == null ? null : tuple.getT2()));
                    } else
                        ret.complete(new TransactionDetail());
                }
        );
        return Mono.fromFuture(ret);
    }


    public Flux<Transaction> loadTransactionsOf(
            String addressOrBlockHash
            , int page
            , int len) {
        if (addressOrBlockHash.length() <= 42)
            return transactionsRepository.findByOwner(addressOrBlockHash
                    , PageRequest.of(page, len, TX_DEFAULT_SORT)
            );
        return transactionsRepository.findByBlockHash(addressOrBlockHash
                , PageRequest.of(page, len, TX_DEFAULT_SORT));
    }

    public Flux<Transaction> loadTransactions(String from, String to, int page, int len) {
        if (from.startsWith("0x") && to.startsWith("0x")) {
            return transactionsRepository.findByFromAndTo(from, to,
                    PageRequest.of(page, len, TX_DEFAULT_SORT));
        } else if (from.startsWith("0x")) {
            return transactionsRepository.findByFrom(from
                    , PageRequest.of(page, len, TX_DEFAULT_SORT));
        } else if (to.startsWith("0x")) {
            return transactionsRepository.findByTo(to
                    , PageRequest.of(page, len, TX_DEFAULT_SORT));
        }
        return loadTransactions(page, len);
    }


    public Flux<ERC20Transfer> loadERC20Transfers(String caddress
            , String address
            , int page
            , int len) {

        if (address.startsWith("0x") && caddress.startsWith("0x")) {
            return erc20TransferRepository.findByContractAndOwner(caddress, address,
                    PageRequest.of(page, len, TX_DEFAULT_SORT));
        } else if (address.startsWith("0x")) {
            return erc20TransferRepository.findByOwner(address
                    , PageRequest.of(page, len, TX_DEFAULT_SORT));
        } else if (caddress.startsWith("0x")) {
            return erc20TransferRepository.findByContract(caddress
                    , PageRequest.of(page, len, TX_DEFAULT_SORT));
        }
        return loadERC20Transfers(page, len);
    }


    public Flux<ERC20Transfer> loadERC20Transfers(int page, int len) {
        return erc20TransferRepository.findAll(PageRequest.of(page, len, TX_DEFAULT_SORT));

    }

}
