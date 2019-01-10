package tech.nilu.platform.explorer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import tech.nilu.platform.explorer.model.Block;

/**
 * Created by mariameda on 2/11/18.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BlockDetail {
    private Block block;
    private Long internalTransactionCount;

    public BlockDetail() {
    }

    public BlockDetail(Block block, Long internalTransactionCount) {
        this.block = block;
        this.internalTransactionCount = internalTransactionCount;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Long getInternalTransactionCount() {
        return internalTransactionCount;
    }

    public void setInternalTransactionCount(Long internalTransactionCount) {
        this.internalTransactionCount = internalTransactionCount;
    }
}
