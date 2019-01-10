package tech.nilu.platform.explorer.web3.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import tech.nilu.platform.explorer.model.Address;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.service.TransactionService;
import tech.nilu.platform.explorer.web3.Web3ModelConverter;

/**
 * Created by mariameda on 2/11/18.
 */
@Component("defaultTransactionHandler")
public class DefaultTransactionHandler implements TransactionHandler {

    private static Log log = LogFactory.getLog(DefaultTransactionHandler.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AddressHandler addressHandler;

    @Autowired
    private Web3ModelConverter converter;

    @Autowired
    private Web3j web3j;

    @Autowired
    ApplicationContext context;

    @Override
    public void onTransactionReceived(Transaction t, Block block) {
        log.debug("transaction: " + t.getFrom() + "->" + t.getTo() + " :" + t.getValue());
        tech.nilu.platform.explorer.model.Transaction tx = converter.from(t);
        tx.setTimestamp(block.getTimestamp());
        transactionService.save(tx)
                .onErrorReturn(new tech.nilu.platform.explorer.model.Transaction())
                .subscribe(res -> {
                    if (res.getTransactionHash() != null) {
                        addressHandler.onMeetInTransaction(tx).subscribe(
                                result -> {
                                    Address from = result.getValue1();
                                    Address to = result.getValue2();
                                    if (to == null || to.getAddress() == null || Boolean.TRUE.equals(to.getContract())) {
                                        web3j.ethGetTransactionReceipt(t.getHash()).observable()
                                                .subscribe(receipt -> {
                                                    if (to == null || to.getAddress() == null) {
                                                        receipt.getTransactionReceipt().ifPresent(ethReceipt -> {
                                                            tx.setContractAddress(ethReceipt.getContractAddress());
                                                            transactionService.save(tx).subscribe();
                                                            addressHandler.handleDeployedContractAddress(ethReceipt.getContractAddress(), tx).subscribe();
                                                        });
                                                    }
                                                    context.getBeansOfType(ContractHandler.class)
                                                            .forEach((name, handler) -> {
                                                                handler.handleTransactionReceipt(tx, to, receipt.getTransactionReceipt().orElse(null));
                                                            });
                                                });
                                    }
                                }
                        );
                    }
                });

    }


}
