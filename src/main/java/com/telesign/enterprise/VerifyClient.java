package com.telesign.enterprise;

import com.telesign.RestClient;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Verify API delivers phone-based verification and two-factor authentication using a time-based, one-time passcode
 * sent via SMS message, Voice call or Push Notification.
 */
public class VerifyClient extends RestClient {

    private static final String VERIFY_SMS_RESOURCE = "/v1/verify/sms";
    private static final String VERIFY_VOICE_RESOURCE = "/v1/verify/call";
    private static final String VERIFY_SMART_RESOURCE = "/v1/verify/smart";
    private static final String VERIFY_STATUS_RESOURCE = "/v1/verify/%s";
    private static final String VERIFY_COMPLETION_RESOURCE = "/v1/verify/completion/%s";
    private static final String SDK_VERSION = VerifyClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();

    /**
     * Constructor for VerifyClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
    */
    public VerifyClient(String customerId, String apiKey) {
        super(customerId, apiKey, "https://rest-ww.telesign.com", SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Constructor for VerifyClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public VerifyClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
	 * The Telesign SMS Verify API allows you to easily integrate frictionless phone number verification
     * on iOS and Android using SMS messages.
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
    public VerifyClient(String customerId,
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
     * The SMS Verify API delivers phone-based verification and two-factor authentication using a time-based,
     * one-time passcode sent over SMS.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-verify-sms for detailed API documentation.
     * 
     * @param phoneNumber
     *         The end user's phone number.
     * @param params
     *         Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse sms(String phoneNumber, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);
        return this.post(VERIFY_SMS_RESOURCE, params);
    }

    /**
     * Use this action to create a verification process for the specified phone number.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/createverificationprocess for detailed API documentation.
     * 
     * @param phoneNumber
     *       The phone number to verify.
     * @param params
     *       Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse createVerificationProcess(String phoneNumber, HashMap<String, Object> params) throws IOException, GeneralSecurityException {

        OmniVerifyClient omniVerifyClient = new OmniVerifyClient(this.customerId, this.apiKey);
        return  omniVerifyClient.createVerificationProcess(phoneNumber, params);
    }

    /**
     * The Voice Verify API delivers patented phone-based verification and two-factor authentication using a one-time
     * passcode sent over verify_voice message.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-verify-call for detailed API documentation.
     * 
     * @param phoneNumber
     *         The end user's phone number.
     * @param params
     *         Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse voice(String phoneNumber, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);

        return this.post(VERIFY_VOICE_RESOURCE, params);
    }

    /**
     * The Smart Verify web service simplifies the process of verifying user identity by integrating several TeleSign
     * web services into a single API call. This eliminates the need for you to make multiple calls to the TeleSign
     * Verify resource.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-smart-verify for detailed API documentation.
     * 
     * @param phoneNumber
     *      The end user's phone number.
     * @param ucid
     *      A string that specifies one of the use case codes.
     * @param params
     *      Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse smart(String phoneNumber, String ucid, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);
        params.put("ucid", ucid);

        return this.post(VERIFY_SMART_RESOURCE, params);
    }

    /**
     * Retrieves the verification result for any verify resource.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-verify-transaction-callback for detailed API documentation.
     * 
     * @param referenceId
     *     The reference_id of the verification process to get the status of.
     * @param params
     *     Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse status(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.get(String.format(VERIFY_STATUS_RESOURCE, referenceId), params);
    }

    /**
     * Notifies TeleSign that a verification was successfully delivered to the user in order to help improve the
     * quality of message delivery routes.
     * <p>
     * See https://developer.telesign.com/docs/completion-service-for-verify-products for detailed API documentation.
     * 
     * @param referenceId
     *     The reference_id of the verification process to notify.
     * @param params
     *     Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse completion(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {
        return this.put(String.format(VERIFY_COMPLETION_RESOURCE, referenceId), params);
    }
}
