package tech.nilu.platform.explorer.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.model.Transaction;

import java.math.BigInteger;

public interface TransactionsRepository extends ReactiveMongoRepository<Transaction, String> {

    Mono<Transaction> findByTransactionHash(Mono<String> hash);

    Flux<Transaction> findByFrom(String from, Pageable pageable);

    Flux<Transaction> findByTo(String to, Pageable pageable);

    Flux<Transaction> findByFromAndTo(String from, String to, Pageable pageable);

    @Query("{ $or: [ { from: ?0}, { to: ?0 }, {contractAddress: ?0} ] }")
    Flux<Transaction> findByOwner(String owner, Pageable pageable);

    @Query(value = "{}", fields = "{'objectContentAsJson':0}")
    Flux<Transaction> findAll(Pageable pageable);

    Mono<Long> countByBlockNumber(BigInteger c);

    Flux<Transaction> findByBlockHash(String hash, Pageable pageable);

    Flux<Transaction> findByBlockHash(String hash);

    Mono<Long> countByTimestampBetween(Long from, Long to);

}

