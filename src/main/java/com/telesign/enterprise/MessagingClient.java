package com.telesign.enterprise;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.HashMap;

public class MessagingClient extends com.telesign.MessagingClient {

    private static final String OMNI_MESSAGING_RESOURCE = "/v1/omnichannel";
    private static final String TEMPLATES_RESOURCE = OMNI_MESSAGING_RESOURCE + "/templates";
    private static final String CAPABILITY_RESOURCE = "/capability";
    private static final String SDK_VERSION = MessagingClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();

    public MessagingClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    public MessagingClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
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
        super(customerId, apiKey, restEndpoint, connectTimeout, readTimeout, writeTimeout, proxy, proxyUsername, proxyPassword, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
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
     * Use this action to check capability of a phone number to use the specified channel.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/checkphonenumberchannelcapability for detailed API documentation.
     * </p>
     */
    public TelesignResponse checkChannelCapability(String channel, String phoneNumber) throws IOException, GeneralSecurityException {
        return this.get(CAPABILITY_RESOURCE + "/"+ channel +"/"+ phoneNumber, new HashMap<>());
    }

    /**
     * Use this action to check capability of a phone number to receive messages from the specified RBM agent.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/checkphonenumberrbmcapability for detailed API documentation.
     * </p>
     */
    public TelesignResponse checkCapabilityRBMAgent(String phoneNumber, String agentId) throws IOException, GeneralSecurityException {
        return this.get(CAPABILITY_RESOURCE + "/rcs/" + phoneNumber + "/" + agentId, new HashMap<>());
    }

    /**
     *
     * Get delivery status and other details for a Telesign Messaging transaction that you have created.
     * Get transaction status can be used for all Messaging API transactions.
     * Also use this endpoint to complete verification, if Telesign generated your code.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getmessagingstatus for detailed API documentation.
     * </p>
     */
    public TelesignResponse getMsgStatus(String referenceId) throws IOException, GeneralSecurityException {
        return getMsgStatus(referenceId, null);
    }


    public TelesignResponse getMsgStatus(String referenceId, String verifyCode) throws IOException, GeneralSecurityException {
        StringBuilder url = new StringBuilder(OMNI_MESSAGING_RESOURCE).append("/").append(referenceId);

        if (verifyCode != null && !verifyCode.trim().isEmpty()) {
            url.append("?verify_code=").append(verifyCode);
        }
        return this.get(url.toString(), new HashMap<>());
    }

    /**
     *
     * Use this action to create a Telesign Messaging template.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/createmsgtemplate for detailed API documentation.
     * </p>
     */
    public TelesignResponse createMsgTemplate(Map<String, Object> params) throws IOException, GeneralSecurityException {
        return this.post(TEMPLATES_RESOURCE, params, JSON_CONTENT_TYPE);
    }

    /**
     *
     * Use this action to get details for the specified Telesign Messaging template.
     *
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getmsgtemplate for detailed API documentation.
     * </p>
     */
    public TelesignResponse getMsgTemplate(String channel, String templateName) throws IOException, GeneralSecurityException {
        return this.get(TEMPLATES_RESOURCE + "/" + channel + "/" + templateName, new HashMap<>());
    }

    /**
     *
     * Use this action to get details for all Telesign Messaging templates associated with this Customer ID.
     *
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getallmsgtemplates for detailed API documentation.
     * </p>
     */
    public TelesignResponse getAllMsgTemplates() throws IOException, GeneralSecurityException {
        return this.get(TEMPLATES_RESOURCE, new HashMap<>());
    }

    /**
     *
     * Use this action to delete a Telesign Messaging template.
     *
     * <p>
     * See https://developer.telesign.com/enterprise/reference/deletemsgtemplate for detailed API documentation.
     * </p>
     */
    public TelesignResponse deleteMsgTemplate(String channel, String templateName) throws IOException, GeneralSecurityException {
        return this.delete(TEMPLATES_RESOURCE + "/" + channel + "/" + templateName, new HashMap<>());
    }
}
