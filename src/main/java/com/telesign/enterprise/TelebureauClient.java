package com.telesign.enterprise;

import com.telesign.RestClient;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * TeleBureau is a service is based on TeleSign's watchlist, which is a proprietary database containing verified phone
 * numbers of users known to have committed online fraud. TeleSign crowd-sources this information from its customers.
 * Participation is voluntary, but you have to contribute in order to benefit.
 */
public class TelebureauClient extends RestClient {

    private static final String TELEBUREAU_CREATE_RESOURCE = "/v1/telebureau/event";
    private static final String TELEBUREAU_RETRIEVE_RESOURCE = "/v1/telebureau/event/%s";
    private static final String TELEBUREAU_DELETE_RESOURCE = "/v1/telebureau/event/%s";
    private static final String SDK_VERSION = TelebureauClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();


    /**
     * Constructor for TelebureauClient.
     * @param customerId
     *        Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
    */
    public TelebureauClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Constructor for TelebureauClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public TelebureauClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
	 * Constructs a new TelebureauClient.
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
    public TelebureauClient(String customerId,
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

    /**
     * Creates a telebureau event corresponding to supplied data.
     * <p>
     * See https://developer.telesign.com/docs/telebureau-api for detailed API documentation.
     * 
     * @param phoneNumber
     *      The phone number to query.
     * @param fraudType
     *      The type of fraud event to report.
     * @param occurredAt
     *      The date and time when the event occurred.
     * @param params
     *      Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse createEvent(String phoneNumber, String fraudType, String occurredAt, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);
        params.put("fraud_type", fraudType);
        params.put("occurred_at", occurredAt);

        return this.post(TELEBUREAU_CREATE_RESOURCE, params);
    }

    /**
     * Creates a telebureau event corresponding to supplied data.
     * <p>
     * See https://developer.telesign.com/docs/telebureau-api for detailed API documentation.
     * 
     * @param referenceId
     *      The reference ID of the event to retrieve.
     * @param params
     *      Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse retrieveEvent(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.get(String.format(TELEBUREAU_RETRIEVE_RESOURCE, referenceId), params);
    }

    /**
     * Deletes a previously submitted fraud event. You make this call in your web application after completion of the
     * create transaction for a telebureau event.
     * <p>
     * See https://developer.telesign.com/docs/telebureau-api for detailed API documentation.
     * 
     * @param referenceId
     *      The reference ID of the event to delete.
     * @param params
     *      Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse deleteEvent(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.delete(String.format(TELEBUREAU_DELETE_RESOURCE, referenceId), params);
    }
}
