package tech.nilu.platform.explorer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariameda on 2/11/18.
 */
@Document(collection = "address")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Address {

    @Id
    private String address;

    private Boolean contract;

    private Boolean erc20support;

    private String name;

    private String symbol;

    private long timestamp;

    private String deployer;

    private List<Address> activeContracts = new ArrayList<>();

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getContract() {
        return contract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setContract(Boolean contract) {
        this.contract = contract;
    }

    public Boolean getErc20support() {
        return erc20support;
    }

    public void setErc20support(Boolean erc20support) {
        this.erc20support = erc20support;
    }

    public List<Address> getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(List<Address> activeContracts) {
        this.activeContracts = activeContracts;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeployer() {
        return deployer;
    }

    public void setDeployer(String deployer) {
        this.deployer = deployer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        return address.equals(address1.address);

    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
