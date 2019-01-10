package tech.nilu.platform.explorer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by mariameda on 2/8/18.
 */
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log {

    private String logIndex;
    private String transactionIndex;
    @Indexed
    private String transactionHash;
    private String blockHash;
    private String blockNumber;
    @Indexed
    private String address;
    private String data;
    private String type;
    private List<String> topics;
    private boolean removed;

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getLogIndex() {
        return logIndex;
    }

    public void setLogIndex(String logIndex) {
        this.logIndex = logIndex;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

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

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
