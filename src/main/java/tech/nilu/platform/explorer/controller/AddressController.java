package tech.nilu.platform.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dao.AddressRepository;
import tech.nilu.platform.explorer.model.Address;

/**
 * Created by mariameda on 2/10/18.
 */
@RestController
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/address/{page}/{length}")
    Flux<Address> loadAddresses(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return addressRepository.findAll(PageRequest.of(page, len));
    }

    @GetMapping("/address/{address}")
    Mono<Address> lookupByAddress(@PathVariable("address") String address) {
        return addressRepository.findById(address);
    }

    @GetMapping("/contracts/{page}/{length}")
    Flux<Address> loadContractAddresses(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return addressRepository.findByContract(true, PageRequest.of(page, len));
    }

    @GetMapping("/tokens/{page}/{length}")
    Flux<Address> loadTokenAddresses(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return addressRepository.findByErc20support(true, PageRequest.of(page, len));
    }
}
