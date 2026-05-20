package com.telesign.enterprise;

import java.io.IOException;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.HashMap;

/**
 * TeleSign's Messaging API allows you to easily send SMS messages. You can send alerts, reminders, and notifications,
 * or you can send verification messages containing one-time passcodes (OTP).
 */
public class MessagingClient extends com.telesign.MessagingClient {

    private static final String OMNI_MESSAGING_RESOURCE = "/v1/omnichannel";
    private static final String TEMPLATES_RESOURCE = OMNI_MESSAGING_RESOURCE + "/templates";
    private static final String CAPABILITY_RESOURCE = "/capability";
    private static final String SDK_VERSION = MessagingClient.class.getPackage().getImplementationVersion();
    private static final String SDK_SOURCE = "java_telesign_enterprise";
    private static final String SDK_VERSION_DEPENDENCY = com.telesign.RestClient.class.getPackage().getImplementationVersion();
    
    /**
     * Constructor for MessagingClient.
     * @param customerId
     *          Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     */
    public MessagingClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com", SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

    /**
     * Constructor for MessagingClient.
     * @param customerId
     *          Your customer_id string associated with your account.
     * @param apiKey
     *        Your api_key string associated with your account.
     * @param restEndpoint
     *         (optional) Override the default restEndpoint to target another endpoint.
     */
    public MessagingClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint, SDK_SOURCE, SDK_VERSION, SDK_VERSION_DEPENDENCY);
    }

     /**
	 * Telesign Messaging is an omnichannel engagement product that allows you to enrich your messages 
     * with the advanced features provided by various channels. 
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
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse omniMessage(Map<String, Object> params) throws IOException, GeneralSecurityException {
        return this.post(OMNI_MESSAGING_RESOURCE, params, JSON_CONTENT_TYPE);
    }

    /**
     * Use this action to check capability of a phone number to use the specified channel.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/checkphonenumberchannelcapability for detailed API documentation.
     * </p>
     * @param channel
     *         The channel to check capability for.
     * @param phoneNumber
     *          The phone number that the service should check for capability to use the specified channel.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse checkChannelCapability(String channel, String phoneNumber) throws IOException, GeneralSecurityException {
        return this.get(CAPABILITY_RESOURCE + "/"+ channel +"/"+ phoneNumber, new HashMap<>());
    }

    /**
     * Use this action to check capability of a phone number to receive messages from the specified RBM agent.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/checkphonenumberrbmcapability for detailed API documentation.
     * </p>
     * @param phoneNumber
     *          The phone number that the service should check for capability to receive messages from the specified RBM agent.
     * @param agentId
     *          The ID of the RBM agent.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
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
     * 
     * @param referenceId
     *        The reference_id of the Telesign Messaging transaction to check.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse getMsgStatus(String referenceId) throws IOException, GeneralSecurityException {
        return getMsgStatus(referenceId, null);
    }

    /**
     * Get delivery status and other details for a Telesign Messaging transaction that you have created.
     * Get transaction status can be used for all Messaging API transactions.
     * Also use this endpoint to complete verification, if Telesign generated your code.
     * <p>
     * See https://developer.telesign.com/enterprise/reference/getmessagingstatus for detailed API documentation.
     * </p>
     * 
     * @param referenceId
     *       The reference_id of the Telesign Messaging transaction to check.
     * @param verifyCode
     *     The verification code for the transaction if Telesign generated the code for you. 
     * @return The TelesignResponse for the request.
     * @throws IOException  if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
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
     * 
     * @param params
     *         All required and optional parameters well-structured according to the API documentation.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
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
     * 
     * @param channel
     *        The channel of the template to get details for.
     * @param templateName
     *       The name of the template to get details for.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
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
     * 
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
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
     * 
     * @param channel
     *        The channel of the template to delete.
     * @param templateName
     *       The name of the template to delete.
     * @return The TelesignResponse for the request.
     * @throws IOException if the HTTP request fails.
     * @throws GeneralSecurityException if there is a security exception.
     */
    public TelesignResponse deleteMsgTemplate(String channel, String templateName) throws IOException, GeneralSecurityException {
        return this.delete(TEMPLATES_RESOURCE + "/" + channel + "/" + templateName, new HashMap<>());
    }
}
