package tech.nilu.platform.explorer.web3.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import tech.nilu.platform.explorer.dao.ERC20TransferRepository;
import tech.nilu.platform.explorer.model.Address;
import tech.nilu.platform.explorer.model.ERC20Transfer;
import tech.nilu.platform.explorer.model.Transaction;
import tech.nilu.platform.explorer.web3.contracts.ERC20Basic;
import tech.nilu.platform.explorer.web3.contracts.ERC20BasicImpl;
import tech.nilu.platform.explorer.web3.contracts.TransferEventResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mariameda on 2/11/18.
 */
@Component("ERC20ContractHandler")
public class ERC20ContractHandler implements ContractHandler {

    @Autowired
    private ERC20TransferRepository transferRepository;

    @Autowired
    private Web3j web3j;

    @Override
    public void handleTransactionReceipt(Transaction transaction,
                                         Address to,
                                         TransactionReceipt receipt) {
        if ( receipt == null )
            return;
        TransactionManager tm = new ReadonlyTransactionManager(web3j
                , receipt.getTo());
        ERC20Basic basic = ERC20BasicImpl.load(receipt.getTo(), web3j
                , tm, BigInteger.ZERO
                , BigInteger.ZERO);
        List<TransferEventResponse> transfers = basic.getTransferEvents(receipt);
        transferRepository.saveAll(
                transfers.stream().map(e -> {
                    ERC20Transfer ret = new ERC20Transfer();
                    ret.setTimestamp(transaction.getTimestamp());
                    ret.setBlockHash(receipt.getBlockHash());
                    ret.setBlockNumber(transaction.getBlockNumber());
                    ret.setTransactionHash(receipt.getTransactionHash());
                    ret.setTransactionIndex(receipt.getTransactionIndex().longValue());
                    ret.setContract(receipt.getTo());
                    ret.setFrom(e._from);
                    ret.setTo(e._recipient);
                    ret.setValue(new BigDecimal(e._value)
                            .divide(BigDecimal.valueOf(Math.pow(10, 18))));
                    return ret;
                }).collect(Collectors.toList())).subscribe();
    }
}
