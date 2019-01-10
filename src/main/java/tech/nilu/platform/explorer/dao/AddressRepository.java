package tech.nilu.platform.explorer.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.model.Address;

/**
 * Created by mariameda on 2/11/18.
 */
public interface AddressRepository extends ReactiveMongoRepository<Address, String> {
    @Query(value = "{}")
    Flux<Address> findAll(Pageable pageable);

    Flux<Address> findByContract(boolean contract, Pageable pageable);

    Flux<Address> findByErc20support(boolean erc20support, Pageable pageable);

    Mono<Long> countByContract(boolean contract);

    Mono<Long> countByErc20support(boolean erc20support);

    Mono<Long> countByTimestampBetween(long s, long e);
    Mono<Long> countByTimestampBetweenAndContract(long s, long e, boolean c);
    Mono<Long> countByTimestampBetweenAndErc20support(long s, long e, boolean t);
}
