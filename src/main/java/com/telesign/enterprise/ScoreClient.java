package com.telesign.enterprise;

import java.net.Proxy;

public class ScoreClient extends com.telesign.ScoreClient {

    private static final String sdkVersion = ScoreClient.class.getPackage().getImplementationVersion();
    private static final String sdkSource = "java_telesign_enterprise";
    private static final String sdkVersionDependency = com.telesign.RestClient.class.getPackage().getImplementationVersion();

    public ScoreClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", sdkSource, sdkVersion, sdkVersionDependency);
    }

    public ScoreClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, sdkSource, sdkVersion, sdkVersionDependency);
    }

    public ScoreClient(String customerId,
                         String apiKey,
                         String restEndpoint,
                         Integer connectTimeout,
                         Integer readTimeout,
                         Integer writeTimeout,
                         Proxy proxy,
                         final String proxyUsername,
                         final String proxyPassword) {
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername, proxyPassword, sdkSource, sdkVersion, sdkVersionDependency);
    }

}
