package com.telesign.enterprise;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.RequestBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * See https://developer.telesign.com/docs/messaging-api for detailed API documentation.
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

    public TelesignResponse omnimessage(String phoneNumber, String message, String messageType, Map<String, String> params) throws IOException, GeneralSecurityException {

        if (params == null) {
            params = new HashMap<>();
        }

        params.put("phone_number", phoneNumber);
        params.put("message", message);
        params.put("message_type", messageType);

        return this.post(OMNI_MESSAGING_RESOURCE, params, JSON_CONTENT_TYPE);
    }

    /**
     * Retrieves the current status of the message.
     * <p>
     * See https://developer.telesign.com/docs/messaging-api for detailed API documentation.
     */
    public TelesignResponse status(String referenceId, Map<String, String> params) throws IOException, GeneralSecurityException {

        return this.get(String.format(MESSAGING_STATUS_RESOURCE, referenceId), params);
    }

    @Override
    public RequestBody createRequestBody(Map<String, ? extends Object> params, String contentType) throws UnsupportedEncodingException, IOException {
        if (contentType.equals(JSON_CONTENT_TYPE)) {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            HashMap<String, Object> paramsCopy = new HashMap<>(params);

            HashMap<String, Object> recipient = new HashMap<>();
            recipient.put("phone_number", paramsCopy.remove("phone_number"));
            jsonObject.add("recipient", gson.toJsonTree(recipient));

            Map<String, Object> message = new HashMap<>();
            Map<String, Object> sms = new HashMap<>();
            Map<String, String> parameters = new HashMap<>();

            parameters.put("text",paramsCopy.remove("message").toString());
            sms.put("parameters",parameters);
            message.put("sms", sms);
            sms.put("template", "text");

            jsonObject.add("message", gson.toJsonTree(message));

            JsonObject paramsObject = gson.toJsonTree(paramsCopy, new TypeToken<HashMap<String, String>>() {
            }.getType()).getAsJsonObject();
            for (String key : paramsCopy.keySet()) {
                jsonObject.add(key, paramsObject.get(key));
            }

            HashMap<String, Object> smsChannel = new HashMap<>();
            smsChannel.put("channel", "sms");
            smsChannel.put("fallback_time", 300);
            List<HashMap<String, Object>> channels = new ArrayList<>();
            channels.add(smsChannel);

            jsonObject.add("channels", gson.toJsonTree(channels));
            String jsonString = jsonObject.toString();
            return !params.isEmpty() ? RequestBody.create(com.telesign.PhoneIdClient.JSON, jsonString.getBytes()) : RequestBody.create(com.telesign.PhoneIdClient.JSON, "");
        } else {
            return super.createRequestBody(params, contentType);
        }
    }
}
