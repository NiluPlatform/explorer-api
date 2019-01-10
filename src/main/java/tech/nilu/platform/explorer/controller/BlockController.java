package tech.nilu.platform.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dto.BlockDetail;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.service.BlockService;

/**
 * Created by mariameda on 2/10/18.
 */
@RestController
public class BlockController {
    @Autowired
    private BlockService service;

    @GetMapping("/blocks/{page}/{length}")
    Flux<Block> loadBlocks(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadBlocks(page, len);
    }

    @GetMapping("/blocks/{hashOrNumber}")
    Mono<BlockDetail> lookupByHash(@PathVariable("hashOrNumber") String hash) {
       return service.lookupByHash(hash);
    }
}
