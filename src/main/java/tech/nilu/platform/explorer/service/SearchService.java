package tech.nilu.platform.explorer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.AddressRepository;
import tech.nilu.platform.explorer.dto.SearchResult;
import tech.nilu.platform.explorer.model.Address;

import java.util.concurrent.CompletableFuture;

/**
 * Created by mariameda on 2/11/18.
 */
@Component
public class SearchService {

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AddressRepository addressRepository;

    public Mono<SearchResult> lookup(String criteria) {
        CompletableFuture<SearchResult> ret = new CompletableFuture<>();
        Mono.zip(transactionService.lookupByHash(criteria)
                , blockService.lookupByHash(criteria)
                , addressRepository.findById(criteria).switchIfEmpty(Mono.just(new Address())))
                .subscribe(
                        tuple -> {
                            ret.complete(new SearchResult(tuple.getT1(),
                                    tuple.getT2(),
                                    tuple.getT3()));
                        }
                );
        return Mono.fromFuture(ret);
    }
}
