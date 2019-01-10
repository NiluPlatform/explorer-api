package tech.nilu.platform.explorer.web3.handler;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.nilu.platform.explorer.model.Address;
import tech.nilu.platform.explorer.model.Transaction;

/**
 * Created by mariameda on 2/11/18.
 */
public interface ContractHandler {
    void handleTransactionReceipt(Transaction transaction,
                                  Address to,
                                  TransactionReceipt receipt);
}
