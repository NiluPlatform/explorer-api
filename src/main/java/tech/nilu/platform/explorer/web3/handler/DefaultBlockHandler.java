package tech.nilu.platform.explorer.web3.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import tech.nilu.platform.explorer.dao.BlockRepository;
import tech.nilu.platform.explorer.dao.TransactionsRepository;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.web3.Web3ModelConverter;

/**
 * Created by mariameda on 2/11/18.
 */
@Component("defaultBlockHandler")
public class DefaultBlockHandler implements BlockHandler {

    private static Log log = LogFactory.getLog(DefaultBlockHandler.class);

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AddressHandler addressHandler;

    @Autowired
    private Web3ModelConverter converter;

    @Autowired
    private TransactionHandler transactionHandler;

    @Override
    public void onBlockReceived(EthBlock block) {
        log.debug("get block#" + block.getBlock().getNumber());
        Block b = converter.from(block);
        addressHandler.handleMinerAddress(block.getBlock().getMiner(), b).subscribe();
        blockRepository.save(b).subscribe();
        if (b.getTxCnt() > 0) {
           block.getBlock().getTransactions().stream().forEach(
                   tr -> transactionHandler.onTransactionReceived((Transaction)tr.get(), b)
           );
        }
        /*transactionsRepository.findByBlockHash(b.getHash()).subscribe(
                    t -> {
                        t.setTimestamp(b.getTimestamp());
                        transactionsRepository.save(t).subscribe();
                    }
        );*/
        //throw new RuntimeException();
    }
}
