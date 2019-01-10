package tech.nilu.platform.explorer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by mariameda on 2/8/18.
 */
@Document(collection = "blocks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Block {
    @Id
    private Long number;
    @Indexed
    private String hash;
    @Indexed
    private String parentHash;
    private String nonce;
    private String sha3Uncles;
    private String logsBloom;
    private String transactionsRoot;
    private String stateRoot;
    private String receiptsRoot;
    private String author;
    private String miner;
    private String mixHash;
    private long difficulty;
    private long totalDifficulty;
    private String extraData;
    private long size;
    private long gasLimit;
    private long gasUsed;
    private long timestamp;
    private int txCnt;
    //private List<String> transactionHashes;
    private List<String> uncles;
    private List<String> sealFields;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSha3Uncles() {
        return sha3Uncles;
    }

    public void setSha3Uncles(String sha3Uncles) {
        this.sha3Uncles = sha3Uncles;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public String getTransactionsRoot() {
        return transactionsRoot;
    }

    public void setTransactionsRoot(String transactionsRoot) {
        this.transactionsRoot = transactionsRoot;
    }

    public String getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(String stateRoot) {
        this.stateRoot = stateRoot;
    }

    public String getReceiptsRoot() {
        return receiptsRoot;
    }

    public void setReceiptsRoot(String receiptsRoot) {
        this.receiptsRoot = receiptsRoot;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

   /*public List<String> getTransactionHashes() {
        return transactionHashes;
    }

    public void setTransactionHashes(List<String> transactionHashes) {
        this.transactionHashes = transactionHashes;
    }*/

    public List<String> getUncles() {
        return uncles;
    }

    public void setUncles(List<String> uncles) {
        this.uncles = uncles;
    }

    public List<String> getSealFields() {
        return sealFields;
    }

    public void setSealFields(List<String> sealFields) {
        this.sealFields = sealFields;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public long getTotalDifficulty() {
        return totalDifficulty;
    }

    public void setTotalDifficulty(long totalDifficulty) {
        this.totalDifficulty = totalDifficulty;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public long getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(long gasUsed) {
        this.gasUsed = gasUsed;
    }

    public int getTxCnt() {
        return txCnt;
    }

    public void setTxCnt(int txCnt) {
        this.txCnt = txCnt;
    }

}
