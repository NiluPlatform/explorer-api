package tech.nilu.platform.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.AddressRepository;
import tech.nilu.platform.explorer.dao.ERC20TransferRepository;
import tech.nilu.platform.explorer.dao.TransactionsRepository;
import tech.nilu.platform.explorer.dto.GenericResponse;

@RestController
public class StatisticsController {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ERC20TransferRepository transferRepository;

    @GetMapping("/stats/transactions/{from}/{to}")
    public Mono<GenericResponse<Long>> countOfTransactions(@PathVariable Long from, @PathVariable Long to) {
      return transactionsRepository.countByTimestampBetween(from, to)
              .map(GenericResponse::new);
    }

    @GetMapping("/stats/transfers/{from}/{to}")
    public Mono<GenericResponse<Long>> countOfTransfers(@PathVariable Long from, @PathVariable Long to) {
        return transferRepository.countByTimestampBetween(from, to).map(GenericResponse::new);
    }

    @GetMapping("/stats/addresses/{from}/{to}")
    public Mono<GenericResponse<Long>> countOfAddresses(@PathVariable Long from, @PathVariable Long to) {
        return addressRepository.countByTimestampBetween(from, to).map(GenericResponse::new);
    }

    @GetMapping("/stats/tokens/{from}/{to}")
    public Mono<GenericResponse<Long>> countOfTokens(@PathVariable Long from, @PathVariable Long to) {
        return addressRepository.countByTimestampBetweenAndErc20support(from, to, true)
                .map(GenericResponse::new);
    }

    @GetMapping("/stats/contracts/{from}/{to}")
    public Mono<GenericResponse<Long>> countOfContracts(@PathVariable Long from, @PathVariable Long to) {
        return addressRepository.countByTimestampBetweenAndContract(from, to, true)
                .map(GenericResponse::new);
    }

    @GetMapping("/stats/transactions")
    public Mono<GenericResponse<Long>> countOfTransactions() {
        return transactionsRepository.count().map(GenericResponse::new);
    }

    @GetMapping("/stats/transfers")
    public Mono<GenericResponse<Long>> countOfTransfers() {
        return transferRepository.count().map(GenericResponse::new);
    }

    @GetMapping("/stats/addresses")
    public Mono<GenericResponse<Long>> countOfAddresses() {
        return addressRepository.count().map(GenericResponse::new);
    }

    @GetMapping("/stats/tokens")
    public Mono<GenericResponse<Long>> countOfTokens() {
        return addressRepository.countByErc20support(true).map(GenericResponse::new);
    }

    @GetMapping("/stats/contracts")
    public Mono<GenericResponse<Long>> countOfContracts() {
        return addressRepository.countByContract(true).map(GenericResponse::new);
    }
}
