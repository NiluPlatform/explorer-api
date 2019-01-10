package tech.nilu.platform.explorer.web3.handler;


import org.web3j.tuples.generated.Tuple2;
import reactor.core.publisher.Mono;
import tech.nilu.platform.explorer.model.Address;
import tech.nilu.platform.explorer.model.Block;
import tech.nilu.platform.explorer.model.Transaction;

/**
 * Created by mariameda on 2/11/18.
 */
public interface AddressHandler {
    Mono<Tuple2<Address,Address>> onMeetInTransaction(Transaction transaction);

    Mono<Address> handleMinerAddress(String address, Block block);

    Mono<Address> handleDeployedContractAddress(String contractAddress, Transaction t);
}
