package tech.nilu.platform.explorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.BlockRepository;
import tech.nilu.platform.explorer.dao.ERC20TransferRepository;
import tech.nilu.platform.explorer.dto.BlockDetail;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.model.ERC20Transfer;

import java.util.concurrent.CompletableFuture;

/**
 * Created by mariameda on 2/11/18.
 */
@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private ERC20TransferRepository transferRepository;


    public Flux<Block> loadBlocks(int page, int len) {
        return blockRepository.findAll(
                PageRequest.of(page, len, Sort.Direction.DESC, "Number")
        );
    }


    public Mono<BlockDetail> lookupByHash(String hashOrNumber) {
        CompletableFuture<BlockDetail> ret = new CompletableFuture<>();
        ERC20Transfer example = new ERC20Transfer();
        try {
            example.setBlockNumber(Long.parseLong(hashOrNumber));
        } catch (Exception e) {
            example.setBlockHash(hashOrNumber);
        }
        Mono.zip(example.getBlockHash() == null ?
                        blockRepository.findByNumber(example.getBlockNumber()).switchIfEmpty(Mono.just(new Block())) :
                        blockRepository.findByHash(hashOrNumber).switchIfEmpty(Mono.just(new Block())),
                transferRepository.count(Example.of(example))
        ).subscribe(tuple -> {
            if (tuple.getT1().getHash() != null)
                ret.complete(new BlockDetail(tuple.getT1(), tuple.getT2()));
            else
                ret.complete(new BlockDetail());
        });

        return Mono.fromFuture(ret);
    }
}
