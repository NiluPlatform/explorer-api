package tech.nilu.platform.explorer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import tech.nilu.platform.explorer.model.ERC20Transfer;
import tech.nilu.platform.explorer.model.Transaction;

/**
 * Created by mariameda on 2/11/18.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionDetail {
    private Transaction transaction;
    private ERC20Transfer erc20Transfer;


    public TransactionDetail() {
    }

    public TransactionDetail(Transaction transaction, ERC20Transfer erc20Transfer) {
        this.transaction = transaction;
        this.erc20Transfer = erc20Transfer;
    }


    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ERC20Transfer getErc20Transfer() {
        return erc20Transfer;
    }

    public void setErc20Transfer(ERC20Transfer erc20Transfer) {
        this.erc20Transfer = erc20Transfer;
    }
}
