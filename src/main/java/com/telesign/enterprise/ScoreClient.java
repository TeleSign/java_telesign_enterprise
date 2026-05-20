package com.telesign.enterprise;

import java.net.Proxy;

/**
 * Score provides risk information about a specified phone number or email address.
 */
public class ScoreClient extends com.telesign.ScoreClient {

    private static final String SDK_VERSION = ScoreClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();
    private static final String DETECT_REST_ENDPOINT = "https://detect.telesign.com";

    /**
     * Constructor for ScoreClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     */
    public ScoreClient(String customerId, String apiKey) {
        super(customerId, apiKey, DETECT_REST_ENDPOINT, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Constructor for ScoreClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public ScoreClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
	 * Telesign Intelligence helps with identifying potentially fraudulent activity by analyzing the transaction risk 
     * associated with a phone number or email address.
	 *
	 * @param customerId
	 *            Your customer_id string associated with your account.
	 * @param apiKey
	 *            Your api_key string associated with your account.
	 * @param restEndpoint
	 *            (optional) Override the default restEndpoint to target another
	 *            endpoint.
	 * @param connectTimeout
	 *            (optional) connectTimeout passed into OkHttp.
	 * @param readTimeout
	 *            (optional) readTimeout passed into OkHttp.
	 * @param writeTimeout
	 *            (optional) writeTimeout passed into OkHttp.
	 * @param proxy
	 *            (optional) proxy passed into OkHttp.
	 * @param proxyUsername
	 *            (optional) proxyUserName used to create an Authenticator passed
	 *            into OkHttp.
	 * @param proxyPassword
	 *            (optional) proxyPassword used to create an Authenticator passed
	 *            into OkHttp.
	 */
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
