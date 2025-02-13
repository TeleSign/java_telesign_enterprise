package com.telesign.enterprise;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class MessagingClient extends com.telesign.RestClient {

    private static final String MESSAGING_RESOURCE = "/v1/messaging";
    private static final String MESSAGING_STATUS_RESOURCE = "/v1/messaging/%s";
    private static final String OMNI_MESSAGING_RESOURCE = "/v1/omnichannel";

    public MessagingClient(String customerId, String apiKey) {

        super(customerId, apiKey, "https://rest-ww.telesign.com");
        configureSslSettings();
    }

    public MessagingClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint);
        configureSslSettings();
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
        configureSslSettings();
    }

    private void configureSslSettings() {
        try {
            // Load the certificate
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getClass().getResourceAsStream("/path/to/your/certificate.crt");
            X509Certificate ca = (X509Certificate) cf.generateCertificate(caInput);

            // Create a KeyStore containing the trusted certificate
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the certificate in the KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            X509TrustManager trustManager = (X509TrustManager) tmf.getTrustManagers()[0];

            // Create an SSLContext that uses the TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new javax.net.ssl.TrustManager[]{trustManager}, null);

            // Set the SSLContext to be used by the RestClient
            setSslSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
