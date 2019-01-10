package tech.nilu.platform.explorer.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.model.ERC20Transfer;

/**
 * Created by mariameda on 2/11/18.
 */
public interface ERC20TransferRepository extends ReactiveMongoRepository<ERC20Transfer, String> {
    @Query(value = "{}")
    Flux<ERC20Transfer> findAll(Pageable pageable);

    Flux<ERC20Transfer> findByContract(String contract,Pageable pageable);

    Mono<ERC20Transfer> findByTransactionHash(Mono<String> hash);

    @Query("{ contract: ?0 , $or: [ { from: ?1}, { to: ?1 } ] }")
    Flux<ERC20Transfer> findByContractAndOwner(String caddress, String address, Pageable pageable);

    @Query("{ $or: [ { from: ?0}, { to: ?0 } ] }")
    Flux<ERC20Transfer> findByOwner(String address, Pageable pageable);

    Mono<Long> countByTimestampBetween(Long from, Long to);
}
