package tech.nilu.platform.explorer.web3.handler;


import org.web3j.protocol.core.methods.response.Transaction;
import tech.nilu.platform.explorer.model.Block;

/**
 * Created by mariameda on 2/11/18.
 */
public interface TransactionHandler {
    void onTransactionReceived(Transaction transaction, Block block);
}
