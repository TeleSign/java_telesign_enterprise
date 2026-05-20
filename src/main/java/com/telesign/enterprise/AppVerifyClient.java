package com.telesign.enterprise;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * AppVerify is a secure, lightweight SDK that integrates a frictionless user verification process into existing
 * native mobile applications.
 **/
public class AppVerifyClient extends com.telesign.AppVerifyClient {

    private static final String APPVERIFY_RESOURCE = "/v1/verify/auto/voice";
    private static final String ENDCALL_RESOURCE = APPVERIFY_RESOURCE + "/finalize";
    private static final String REPORT_CALLERID_RESOURCE = APPVERIFY_RESOURCE + "/finalize/callerid";
    private static final String REPORT_TIMEOUT_RESOURCE = APPVERIFY_RESOURCE + "/finalize/timeout";
    private static final String STATUS_RESOURCE = APPVERIFY_RESOURCE + "/%s";
    private static final String SENDCODE_RESOURCE = APPVERIFY_RESOURCE + "/initiate";
    private static final String SDK_VERSION = AppVerifyClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();

    /**
     * Constructor for AppVerifyClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     */
    public AppVerifyClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Constructor for AppVerifyClient.
     * @param customerId
     *         Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public AppVerifyClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
	 * The Telesign App Verify API allows you to easily integrate frictionless phone number verification
     * on iOS and Android using flash calls.
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
    public AppVerifyClient(String customerId,
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
     * Use this endpont to initiate verification of the specified phone number using the Telesign App Verify API.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/sendappverifycode for detailed API documentation.
     * 
     * @param phoneNumber
     *          The phone number to verify.
     * @param params
     *          Additional parameters for the request.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse sendCode(String phoneNumber, HashMap<String, String> params) throws IOException, GeneralSecurityException {
        params.put("phone_number", phoneNumber);
        return this.post(SENDCODE_RESOURCE, params);
    }

    /**
     * Use this endpoint to terminate a call created using the Telesign App Verify API if the handset does not terminate the call in your application.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/endappverifycall for detailed API documentation.
     * 
     * @param referenceId
     *         The reference_id of the verification call to terminate.
     * @param verifyCode
     *         The verification code for the call.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse endCall(String referenceId, String verifyCode) throws IOException, GeneralSecurityException {
        Map<String, String> params = new HashMap<>();
        params.put("reference_id", referenceId);

        if (verifyCode != null && !verifyCode.isEmpty()) {
            params.put("verify_code", verifyCode);
        }
        
        return this.post(ENDCALL_RESOURCE, params);
    }

    /**
     * If a Telesign App Verify API call is unsuccessful, the device will not receive the call.
     * If there is a prefix sent by Telesign in the initiate request and it cannot be matched to the CLI of the verification call,
     * you can use this endpoint to report the issue to Telesign for troubleshooting
     * <p>
     * See https://developer.telesign.com/enterprise/reference/reportappverifycallerid for detailed API documentation.
     * 
     * @param referenceId
     *        The reference_id of the verification call to report.
     * @param unknownCallerId
     *       The caller ID that was not recognized.
     * @param customerId
     *       Your customer_id string associated with your account.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse reportCallerId(String referenceId, String unknownCallerId, String customerId) throws IOException, GeneralSecurityException {
        Map<String, String> params = new HashMap<>();
        params.put("reference_id", referenceId);
        params.put("unknown_caller_id", unknownCallerId);

        if (customerId != null && !customerId.isEmpty()) {
            params.put("customer_id", customerId);
        }

        return this.post(REPORT_CALLERID_RESOURCE, params);
    }

    /**
     * If a mobile device verification call does not make it to the designated handset within the specified amount of time, you can use the Finalize Timeout endpoint to report the issue to Telesign.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/reportappverifytimeout for detailed API documentation.
     * 
     * @param referenceId
     *        The reference_id of the verification call to report.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse reportTimeout(String referenceId) throws IOException, GeneralSecurityException {
        Map<String, String> params = new HashMap<>();
        params.put("reference_id", referenceId);
        return this.post(REPORT_TIMEOUT_RESOURCE, params);
    }

    /**
     * Use this endpoint to get the status of a Telesign App Verify API request that you initiated
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getappverifystatus for detailed API documentation.
     * 
     * @param referenceId
     *        The reference_id of the verification call to check.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse getStatus(String referenceId) throws IOException, GeneralSecurityException {
        return this.get(String.format(STATUS_RESOURCE, referenceId), null);
    }
}
