package tech.nilu.platform.explorer.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.model.Block;

public interface BlockRepository extends ReactiveMongoRepository<Block, String> {

  Mono<Block> findByHash(String hash);

  Mono<Block> findByNumber(long number);

  @Query(value = "{}")
  Flux<Block> findAll(Pageable pageable);


}

