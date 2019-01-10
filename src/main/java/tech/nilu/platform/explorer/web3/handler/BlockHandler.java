package tech.nilu.platform.explorer.web3.handler;

import org.web3j.protocol.core.methods.response.EthBlock;

/**
 * Created by mariameda on 2/11/18.
 */
public interface BlockHandler {
    void onBlockReceived(EthBlock block);
}
