package com.telesign.enterprise;

import com.telesign.RestClient;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OmniVerifyClient extends RestClient {
    private static final String VERIFY_STATUS_RESOURCE = "/v1/verify/%s";
    private static final String sdkVersion = VerifyClient.class.getPackage().getImplementationVersion();
    private static final String sdkSource = "java_telesign_enterprise";
    private static final String sdkVersionDependency = com.telesign.RestClient.class.getPackage()
            .getImplementationVersion();


    private static final String PATH_VERIFICATION = "/verification";
    private static final String PATH_VERIFICATION_RESOURCE = PATH_VERIFICATION + "/%s";

    public OmniVerifyClient(String customerId, String apiKey) {
        super(customerId, apiKey, "https://verify.telesign.com", sdkSource, sdkVersion,
                sdkVersionDependency);
    }

    public OmniVerifyClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, sdkSource, sdkVersion, sdkVersionDependency);
    }

    public OmniVerifyClient(String customerId, String apiKey, String restEndpoint, Integer connectTimeout,
                            Integer readTimeout, Integer writeTimeout, Proxy proxy, final String proxyUsername,
                            final String proxyPassword) {
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername,
                proxyPassword, sdkSource, sdkVersion, sdkVersionDependency);
    }

    /**
     * Use this action to create a verification process for the specified phone number.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/createverificationprocess for detailed API documentation.
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
     * Use this action to get a verification process for the specified reference id.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getverificationprocess for detailed API documentation.
     */
    public TelesignResponse getVerificationProcess(String referenceId, HashMap<String, String> params)
            throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        return this.get(String.format(PATH_VERIFICATION_RESOURCE, referenceId), params);
    }
}