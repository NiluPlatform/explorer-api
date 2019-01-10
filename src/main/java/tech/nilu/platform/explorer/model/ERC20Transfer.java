package tech.nilu.platform.explorer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Created by mariameda on 2/11/18.
 */
@Document(collection = "erc20")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ERC20Transfer {

    @JsonProperty("hash")
    @Id
    private String transactionHash;

    private Long transactionIndex;

    @Indexed
    private String blockHash;

    @Indexed
    private long blockNumber;

    @Indexed
    private String contract;

    @Indexed
    private String from;

    @Indexed
    private String to;

    private BigDecimal value;

    private long timestamp;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
