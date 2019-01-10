package tech.nilu.platform.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dto.SearchResult;
import tech.nilu.platform.explorer.service.SearchService;

/**
 * Created by mariameda on 2/11/18.
 */
@RestController
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("/lookup/{criteria}")
    Mono<SearchResult> lookupByHash(@PathVariable("criteria") String criteria) {
        return service.lookup(criteria);
    }
}
