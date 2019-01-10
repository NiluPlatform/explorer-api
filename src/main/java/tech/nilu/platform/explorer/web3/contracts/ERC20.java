package tech.nilu.platform.explorer.web3.contracts;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import rx.Observable;

import java.math.BigInteger;
import java.util.List;

public interface ERC20 extends ERC20Basic {
    List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt);

    Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock);

    RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value);

    RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value);

    RemoteCall<BigInteger> allowance(String _owner, String _spender);

}