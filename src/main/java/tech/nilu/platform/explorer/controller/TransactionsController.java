package tech.nilu.platform.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.dto.TransactionDetail;
import tech.nilu.platform.explorer.model.ERC20Transfer;
import tech.nilu.platform.explorer.model.Transaction;
import tech.nilu.platform.explorer.service.TransactionService;

@RestController
class TransactionsController {

    @Autowired
    private TransactionService service;


    @GetMapping("/transactions/{page}/{length}")
    Flux<Transaction> loadTransactions(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadTransactions(page, len);
    }

    @GetMapping("/transactions/{txHash}")
    Mono<TransactionDetail> lookupByHash(@PathVariable("txHash") String hash) {
        return service.lookupByHash(hash);
    }


    @GetMapping({"/transactions/{addressOrBlockHash}/{page}/{length}"})
    Flux<Transaction> loadTransactionsOf(
            @PathVariable("addressOrBlockHash") String addressOrBlockHash
            , @PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadTransactionsOf(addressOrBlockHash, page, len);
    }

    @GetMapping({"/transactions/{from}/{to}/{page}/{length}"})
    Flux<Transaction> loadTransactionsOf(
            @PathVariable("from") String from
            , @PathVariable("to") String to
            , @PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadTransactions(from.toLowerCase(), to.toLowerCase(), page, len);
    }

    @GetMapping({"/transfers/{caddress}/{address}/{page}/{length}"})
    Flux<ERC20Transfer> loadERC20Transfers(
            @PathVariable("caddress") String caddress
            , @PathVariable("address") String address
            , @PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadERC20Transfers(caddress.toLowerCase(), address.toLowerCase(), page, len);
    }

    @GetMapping({"/transfers/{page}/{length}"})
    Flux<ERC20Transfer> loadERC20Transfers(@PathVariable("page") int page
            , @PathVariable("length") int len) {
        return service.loadERC20Transfers(page, len);
    }

}

