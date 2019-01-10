package tech.nilu.platform.explorer.config.auto;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import tech.nilu.platform.explorer.web3.Web3jHealthIndicator;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * web3j auto configuration for Spring Boot.
 */
@Configuration
@ConditionalOnClass(Web3j.class)
@EnableConfigurationProperties(Web3Config.class)
public class Web3jAutoConfiguration {

    private static Log log = LogFactory.getLog(Web3jAutoConfiguration.class);

    @Autowired
    private Web3Config config;

    @Bean
    @ConditionalOnMissingBean
    public Web3j web3j() {
        Web3jService web3jService = buildService(config.getServerAddress());
        log.info("Building service for endpoint: " + config.getServerAddress());
        return Web3j.build(web3jService);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = Web3Config.WEB3_PREFIX, name = "admin-client", havingValue = "true")
    public Admin admin() {
        Web3jService web3jService = buildService(config.getServerAddress());
        log.info("Building admin service for endpoint: " + config.getServerAddress());
        return Admin.build(web3jService);
    }

    private Web3jService buildService(String clientAddress) {
        Web3jService web3jService;

        if (clientAddress == null || clientAddress.equals("")) {
            web3jService = new HttpService(createOkHttpClient());
        } else if (clientAddress.startsWith("http")) {
            web3jService = new HttpService(clientAddress, createOkHttpClient(), false);
        } else if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            web3jService = new WindowsIpcService(clientAddress);
        } else {
            web3jService = new UnixIpcService(clientAddress);
        }

        return web3jService;
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureSSL(builder);
        configureLogging(builder);
        configureTimeouts(builder);
        return builder.build();
    }

    private void configureSSL(OkHttpClient.Builder builder) {
        if (config.isSslVerificationIgnored()) {
            try {
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                    throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                    throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void configureTimeouts(OkHttpClient.Builder builder) {
        Long tos = config.getTimeout();
        if (tos != null) {
            builder.connectTimeout(tos, TimeUnit.SECONDS);
            builder.readTimeout(tos, TimeUnit.SECONDS);  // Sets the socket timeout too
            builder.writeTimeout(tos, TimeUnit.SECONDS);
        }
    }

    private static void configureLogging(OkHttpClient.Builder builder) {
        if (log.isDebugEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::debug);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }


    @Bean
    @ConditionalOnBean(Web3j.class)
    Web3jHealthIndicator web3jHealthIndicator(Web3j web3j) {
        return new Web3jHealthIndicator(web3j);
    }
}