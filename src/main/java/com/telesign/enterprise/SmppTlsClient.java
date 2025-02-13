package com.telesign.enterprise;

import com.telesign.RestClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SmppTlsClient extends RestClient {

    public SmppTlsClient(String customerId, String apiKey, String restEndpoint) {
        super(customerId, apiKey, restEndpoint);
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
}
