package tech.nilu.platform.explorer.config.auto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by mariameda on 2/8/18.
 */
@EnableConfigurationProperties
@ConfigurationProperties(prefix = Web3Config.WEB3_PREFIX)
public class Web3Config {

    public final static String WEB3_PREFIX = "web3";

    private String serverAddress;
    private Long timeout;
    private boolean sslVerificationIgnored;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }


    public boolean isSslVerificationIgnored() {
        return sslVerificationIgnored;
    }

    public void setSslVerificationIgnored(boolean sslVerificationIgnored) {
        this.sslVerificationIgnored = sslVerificationIgnored;
    }
}
