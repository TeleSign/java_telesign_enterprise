package com.telesign.enterprise;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * PhoneID is a set of REST APIs that deliver deep phone number data attributes that help optimize the end user
 * verification process and evaluate risk.
 * <p>
 * TeleSign PhoneID provides a wide range of risk assessment indicators on the number to help confirm user identity,
 * delivering real-time decision making throughout the number lifecycle and ensuring only legitimate users are
 * creating accounts and accessing your applications.
 */
public class PhoneIdClient extends com.telesign.PhoneIdClient {

    private static final String PHONEID_RESOURCE = "/v1/phoneid";
    private static final String PHONEID_STANDARD_RESOURCE = PHONEID_RESOURCE + "/standard/%s";
    private static final String PHONEID_SCORE_RESOURCE = PHONEID_RESOURCE + "/score/%s";
    private static final String PHONEID_LIVE_RESOURCE = PHONEID_RESOURCE + "/live/%s";
    private static final String PHONEID_NUMBER_DEACTIVATION_RESOURCE = PHONEID_RESOURCE + "/number_deactivation/%s";
    private static final String PHONEID_GET_INFO_RESOURCE = PHONEID_RESOURCE + "/%s";
    private static final String sdkVersion = PhoneIdClient.class.getPackage().getImplementationVersion();
    private static final String sdkSource = "java_telesign_enterprise";
    private static final String sdkVersionDependency = com.telesign.RestClient.class.getPackage().getImplementationVersion();


    public PhoneIdClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", sdkSource, sdkVersion, sdkVersionDependency);
    }

    public PhoneIdClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, sdkSource, sdkVersion, sdkVersionDependency);
    }

    public PhoneIdClient(String customerId,
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

    /**
     * PhoneID is a set of REST APIs that deliver deep phone number data attributes that help optimize the end user
     * verification process and evaluate risk.
     * <p>
     * TeleSign PhoneID provides a wide range of risk assessment indicators on the number to help confirm user identity,
     * delivering real-time decision making throughout the number lifecycle and ensuring only legitimate users are
     * creating accounts and accessing your applications.
     */
    public TelesignResponse standard(String phoneNumber, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.get(String.format(PHONEID_STANDARD_RESOURCE, phoneNumber), params);
    }

    /**
     * Score is an API that delivers reputation scoring based on phone number intelligence, traffic patterns, machine
     * learning, and a global data consortium.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-phoneid-score for detailed API documentation.
     */
    public TelesignResponse score(String phoneNumber, String ucid, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("ucid", ucid);

        return this.get(String.format(PHONEID_SCORE_RESOURCE, phoneNumber), params);
    }

    /**
     * The PhoneID Live API delivers insights such as whether a phone is active or disconnected, a device is reachable
     * or unreachable and its roaming status.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-phoneid-live for detailed API documentation.
     */
    public TelesignResponse live(String phoneNumber, String ucid, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("ucid", ucid);

        return this.get(String.format(PHONEID_LIVE_RESOURCE, phoneNumber), params);
    }

    /**
     * The PhoneID Number Deactivation API determines whether a phone number has been deactivated and when, based on
     * carriers' phone number data and TeleSign's proprietary analysis.
     * <p>
     * See https://developer.telesign.com/docs/rest_api-phoneid-number-deactivation for detailed API documentation.
     */
    public TelesignResponse numberDeactivation(String phoneNumber, String ucid, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("ucid", ucid);

        return this.get(String.format(PHONEID_NUMBER_DEACTIVATION_RESOURCE, phoneNumber), params);
    }

    /**
     * Enter a phone number with country code to receive detailed information about carrier, location, and other details.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/submitphonenumberforidentity for detailed API documentation.
     */

    public TelesignResponse getInfo(String phoneNumber, Map<String, Object> params) throws IOException, GeneralSecurityException {
        if (params == null) {
            params = new HashMap<>();
        }

        if (!params.containsKey("consent")) {
            Map<String, Object> consent = new HashMap<>();
            consent.put("method", 1);
            params.put("consent", consent);
        }

        String url = String.format(PHONEID_GET_INFO_RESOURCE, phoneNumber);

        return this.post(url, params, "application/json", "Basic");
    }

    /**
     * Enter a phone number with country code to receive detailed information about carrier, location, and other details.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/submitphonenumberforidentityalt for detailed API documentation.
     */

    public TelesignResponse getInfoAlt(String phoneNumber, Map<String, Object> params) throws IOException, GeneralSecurityException {
        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);

        if (!params.containsKey("consent")) {
            Map<String, Object> consent = new HashMap<>();
            consent.put("method", 1);
            params.put("consent", consent);
        }

        return this.post(PHONEID_RESOURCE, params, "application/json", "Basic");
    }
}
