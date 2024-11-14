package com.telesign.enterprise;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class MessagingClient extends com.telesign.RestClient {

    private static final String MESSAGING_RESOURCE = "/v1/messaging";
    private static final String MESSAGING_STATUS_RESOURCE = "/v1/messaging/%s";
    private static final String OMNI_MESSAGING_RESOURCE = "/v1/omnichannel";

    public MessagingClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com");
    }

    public MessagingClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint);
    }

    public MessagingClient(String customerId,
                         String apiKey,
                         String restEndpoint,
                         Integer connectTimeout,
                         Integer readTimeout,
                         Integer writeTimeout,
                         Proxy proxy,
                         final String proxyUsername,
                         final String proxyPassword) {
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername, proxyPassword);
    }

    /**
     * Send a message to the target phone_number.
     * @param phoneNumber The end user's phone number with country code included.
     * @param message Text of the message to be sent to the end user.
     * @param messageType This parameter specifies the traffic type being sent in the message.
     * @param params Additional and optional params
     * <p>
     * See  https://developer.telesign.com/enterprise/reference/sendsms for detailed API documentation.
     * </p>
     */
    public TelesignResponse message(String phoneNumber, String message, String messageType, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);
        params.put("message", message);
        params.put("message_type", messageType);

        return this.post(MESSAGING_RESOURCE, params);
    }

    /**
     * Send a message to the target recipient using any of Telesign's supported channels.
     * @param params All required and optional parameters well-structured according to the API documentation.
     * <p>
     * See  https://developer.telesign.com/enterprise/reference/sendadvancedmessage for detailed API documentation.
     * </p>
     */
    public TelesignResponse omniMessage(Map<String, Object> params) throws IOException, GeneralSecurityException {
        return this.post(OMNI_MESSAGING_RESOURCE, params, JSON_CONTENT_TYPE);
    }

    /**
     * Retrieves the status of requests initiated via the "message" method in this class
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getsmsstatus for detailed API documentation.
     */
    public TelesignResponse status(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.get(String.format(MESSAGING_STATUS_RESOURCE, referenceId), params);
    }
}
