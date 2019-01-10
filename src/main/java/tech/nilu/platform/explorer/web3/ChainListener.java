package tech.nilu.platform.explorer.web3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import tech.nilu.platform.explorer.dao.BlockRepository;
import tech.nilu.platform.explorer.service.TransactionService;
import tech.nilu.platform.explorer.web3.handler.BlockHandler;
import tech.nilu.platform.explorer.web3.handler.TransactionHandler;

import java.math.BigInteger;

/**
 * Created by mariameda on 2/8/18.
 */

@Component
public class ChainListener implements ApplicationListener<ApplicationContextEvent>, Ordered {

    private static Log log = LogFactory.getLog(ChainListener.class);

    @Autowired
    private Web3j web3j;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BlockHandler blockHandler;

    @Autowired
    private TransactionHandler transactionHandler;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        blockRepository.count().subscribe(this::subscribeEventListeners);
        //findBlockAndSubscribeTransactionListener();
    }

    private void subscribeEventListeners(long c) {
        subscribeBlockListener(BigInteger.valueOf(c));
    }

    /*private void findBlockAndSubscribeTransactionListener() {
        transactionService.loadLastBlockNumber()
                .subscribe(this::subscribeTransactionListener);
    }*/

    private void subscribeBlockListener(BigInteger c) {
        log.debug("subscribe block listener from block#" + c);
        web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
                new DefaultBlockParameterNumber(Math.max(c.longValue() - 10, 0)), true)
                .doOnTerminate(this::restartBlockListener)
                .subscribe(s -> {
                    blockHandler.onBlockReceived(s);
                });
    }

    private void restartBlockListener() {
        log.error("Restart Block Listener");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blockRepository.count().subscribe(this::subscribeEventListeners);
    }

    /*private void subscribeTransactionListener(BigInteger c) {
        log.debug("subscribe tx listener from block#" + c);
        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                new DefaultBlockParameterNumber(c))
                .doOnTerminate(this::restartTransactionListener)
                .subscribe(transactionHandler::onTransactionReceived);
    }

    private void restartTransactionListener() {
        log.error("Restart Transaction Listener");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        findBlockAndSubscribeTransactionListener();
    }*/

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
