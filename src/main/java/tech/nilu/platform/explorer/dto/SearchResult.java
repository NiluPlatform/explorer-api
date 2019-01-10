package tech.nilu.platform.explorer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import tech.nilu.platform.explorer.model.Address;

/**
 * Created by mariameda on 2/11/18.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchResult {
    private Address address;
    private BlockDetail blockDetail;
    private TransactionDetail transactionDetail;
    private String type;

    public SearchResult(Address address) {
        this.address = address;
        type = "address";
    }

    public SearchResult(BlockDetail blockDetail) {
        this.blockDetail = blockDetail;
        type = "block";
    }

    public SearchResult(TransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
        type = "transaction";
    }

    public SearchResult(TransactionDetail t1, BlockDetail t2, Address t3) {
        if (t1 != null && t1.getTransaction() != null) {
            this.transactionDetail = t1;
            type = "transaction";
        } else if (t2 != null && t2.getBlock() != null) {
            this.blockDetail = t2;
            type = "block";
        }else if (t3 != null) {
            this.address = t3;
            type = "address";
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BlockDetail getBlockDetail() {
        return blockDetail;
    }

    public void setBlockDetail(BlockDetail blockDetail) {
        this.blockDetail = blockDetail;
    }

    public TransactionDetail getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(TransactionDetail transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
