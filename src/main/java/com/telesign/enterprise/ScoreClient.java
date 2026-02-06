package com.telesign.enterprise;

import java.net.Proxy;

public class ScoreClient extends com.telesign.ScoreClient {

    private static final String SDK_VERSION = ScoreClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();
    private static final String DETECT_REST_ENDPOINT = "https://detect.telesign.com";

    public ScoreClient(String customerId, String apiKey) {
        super(customerId, apiKey, DETECT_REST_ENDPOINT, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    public ScoreClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
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
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername, proxyPassword, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

}
