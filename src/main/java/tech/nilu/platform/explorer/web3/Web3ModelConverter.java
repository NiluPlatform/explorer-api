package tech.nilu.platform.explorer.web3;

import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.model.Transaction;

import java.math.BigDecimal;

/**
 * Created by mariameda on 2/11/18.
 */
@Component
public class Web3ModelConverter {

    public Block from(EthBlock b){
        EthBlock.Block ethBlock = b.getBlock();
        Block block = new Block();
        block.setAuthor(ethBlock.getAuthor());
        block.setDifficulty(ethBlock.getDifficulty().longValue());
        block.setTotalDifficulty(ethBlock.getTotalDifficulty().longValue());
        block.setExtraData(ethBlock.getExtraData());
        block.setGasLimit(ethBlock.getGasLimit().longValue());
        block.setGasUsed(ethBlock.getGasUsed().longValue());
        block.setHash(ethBlock.getHash());
        block.setLogsBloom(ethBlock.getLogsBloom());
        block.setMiner(ethBlock.getMiner());
        block.setMixHash(ethBlock.getMixHash());
        block.setNonce(ethBlock.getNonceRaw());
        block.setNumber(ethBlock.getNumber().longValue());
        block.setParentHash(ethBlock.getParentHash());
        block.setReceiptsRoot(ethBlock.getReceiptsRoot());
        block.setSealFields(ethBlock.getSealFields());
        block.setSha3Uncles(ethBlock.getSha3Uncles());
        block.setSize(ethBlock.getSize().longValue());
        block.setStateRoot(ethBlock.getStateRoot());
        block.setTimestamp(ethBlock.getTimestamp().longValue());
        block.setTransactionsRoot(ethBlock.getTransactionsRoot());
        block.setTxCnt(ethBlock.getTransactions().size());
        /*block.setTransactionHashes(ethBlock.getTransactions().stream().map(
                t -> {
                    if (t.get() instanceof EthBlock.TransactionHash) {
                        return ((EthBlock.TransactionHash) t.get()).get();
                    } else {
                        return ((EthBlock.TransactionObject) t.get()).get().getHash();
                    }
                }).collect(Collectors.toList()));*/
        return block;
    }

    public Transaction from(org.web3j.protocol.core.methods.response.Transaction t){
        Transaction transaction = new Transaction();
        transaction.setBlockHash(t.getBlockHash());
        transaction.setBlockNumber(t.getBlockNumber().longValue());
        transaction.setFrom(t.getFrom());
        transaction.setTo(t.getTo());
        transaction.setCreates(t.getCreates());
        transaction.setGas(t.getGas());
        transaction.setGasPrice(t.getGasPrice());
        transaction.setInput(t.getInput());
        transaction.setPublicKey(t.getPublicKey());
        transaction.setR(t.getR());
        transaction.setS(t.getS());
        transaction.setRaw(t.getRaw());
        transaction.setNonce(t.getNonceRaw());
        transaction.setValue(new BigDecimal(t.getValue()).divide(new BigDecimal("1000000000000000000")));
        transaction.setV(t.getV());
        transaction.setTransactionIndex(t.getTransactionIndex().longValue());
        transaction.setTransactionHash(t.getHash());
        return transaction;
    }
}
