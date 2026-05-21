package com.telesign.enterprise;

import com.telesign.RestClient;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * The Telesign Omni Verify API allows you to easily integrate frictionless phone number verification
 * on iOS and Android using multiple verification methods.
 */
public class OmniVerifyClient extends RestClient {
    private static final String SDK_VERSION = VerifyClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage()
            .getImplementationVersion();


    private static final String PATH_VERIFICATION = "/verification";
    private static final String PATH_VERIFICATION_RESOURCE = PATH_VERIFICATION + "/%s";
    private static final String PATH_VERIFICATION_RESOURCE_UPDATE = PATH_VERIFICATION + "/%s/state";
    
    /**
     * Create a new instance of the OmniVerifyClient
     * @param customerId
     *       Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     */
    public OmniVerifyClient(String customerId, String apiKey) {
        super(customerId, apiKey, "https://verify.telesign.com", SDK_SOURCE, SDK_VERSION,
                SDK_VERSION_DEPENDENCY);
    }

    /**
     * Create a new instance of the OmniVerifyClient
     * @param customerId
     *      Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public OmniVerifyClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
	 * The Telesign Omni Verify API allows you to easily integrate frictionless phone number verification
     * on iOS and Android using multiple verification methods.
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
    public OmniVerifyClient(String customerId, String apiKey, String restEndpoint, Integer connectTimeout,
                            Integer readTimeout, Integer writeTimeout, Proxy proxy, final String proxyUsername,
                            final String proxyPassword) {
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername,
                proxyPassword, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Use this action to create a verification process for the specified phone number.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/createverificationprocess for detailed API documentation.
     * 
     * @param phoneNumber
     *       The phone number to verify.
     * @param params
     *      All required and optional parameters well-structured according to the API documentation.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse createVerificationProcess(String phoneNumber, HashMap<String, Object> params)
            throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        HashMap<String, String> infoRecipient = new HashMap<>();
        infoRecipient.put("phone_number", phoneNumber);
        params.put("recipient", infoRecipient);

        if (!params.containsKey("verification_policy")) {
            List<Object> infoVerificationPolicy = new ArrayList<>();
            HashMap<String, String> infoMethod = new HashMap<>();
            infoMethod.put("method", "sms");
            infoVerificationPolicy.add(infoMethod);
            params.put("verification_policy", infoVerificationPolicy);
        }
        return this.post(PATH_VERIFICATION, params, "application/json", "Basic");
    }

    /**
     * Use this action to get a verification process for the specified reference ID.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getverificationprocess for detailed API documentation.
     * 
     * @param referenceId
     *       The reference_id of the verification process to get.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse getVerificationProcess(String referenceId)
            throws IOException, GeneralSecurityException {

        return this.get(String.format(PATH_VERIFICATION_RESOURCE, referenceId), null);
    }

    /**
     * Use this action to update a verification process for the specified reference ID.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/updateverificationprocess for detailed API documentation.
     * 
     * @param referenceId
     *      The reference_id of the verification process to update.
     * @param action
     *     Specifies how you want to change the verification state for this verification process.
     * @param securityFactor
     *      If an OTP was sent or generated, include the user’s asserted code so Telesign can confirm or complete the verification.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse updateVerificationProcess(String referenceId, String action, String securityFactor)
            throws IOException, GeneralSecurityException {

        Map<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put("security_factor", securityFactor);

        return patch(String.format(PATH_VERIFICATION_RESOURCE_UPDATE, referenceId), params, JSON_CONTENT_TYPE, AUTH_BASIC);
    }
}