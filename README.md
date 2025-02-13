# Telesign Full-service Java SDK

[Telesign](https://telesign.com) connects, protects, and defends the customer experience with intelligence from billions of digital interactions and mobile signals. Through developer-friendly APIs that deliver user verification, digital identity, and omnichannel communications, we help the world's largest brands secure onboarding, maintain account integrity, prevent fraud, and streamline omnichannel engagement.

## Requirements

* **JDK 7+**
* ***_(Optional)_*** **Gradle** This package manager isn't required to use this SDK, but it is required to use the installation instructions below.
* ***_(Optional)_*** **Kotlin** This domain-specific langage (DSL) isn't required to use this SDK, but it is required to use the installation instructions below. These installation steps rely on using Kotlin as your DSL for the build script in the Gradle project. Some steps need to be adapted if you are using Groovy as your DSL.

> NOTE:
>
> These instructions are for MacOS. They will need to be adapted if you are installing on Windows.

## Installation

Follow these steps to add this SDK as a dependency to your project.

1. Create a directory where you want to create your Telesign integration. Skip this step if you already have created a project. If you plan to create multiple Java projects that use Telesign, we recommend that you group them within a `telesign_integrations` directory.

```
   cd ~/code/local
   mkdir telesign_integrations
   cd telesign_integrations
   mkdir {your project directory name}
   cd {your project directory name}
```

2. Create a new Gradle project.

   `gradle init`

3. Select the following options at each of the prompts that appear at the command line.

* **Select type of project to generate:** `2: application`
* **Select implementation language:** `3: Java`
* **Split functionality across multiple subprojects?:** `1: no - only one application project`
* **Select build script DSL:** `2: Kotlin`
* **Generate build using new APIs and behavior:** `yes` 
* **Select test framework:** {your preferred test framework}
* **Project name (default: {default project name}):** {your project name}
* **Source package (default: {default source package name}):** {your source package name}

You should then see a message that indicates the build was successful.

4. Add the following dependency to the `dependencies` block in the file "app/build.gradle.kts" in your project:

   ```
   implementation("com.telesign.enterprise:telesignenterprise:2.2.3")
   ```

This pulls in the Telesign Full-service Java SDK.

This will also install the Telesign Self-service Java SDK since it is a dependency for the Full-service SDK.

## Authentication

If you use a Telesign SDK to make your request, authentication is handled behind-the-scenes for you. All you need to provide is your Customer ID and API Key. The SDKs apply Digest authentication whenever they make a request to a Telesign service where it is supported. When Digest authentication is not supported, the SDKs apply Basic authentication.

## SMPP TLSv1.2 Configuration

To configure SMPP TLSv1.2 in your Java application, follow these steps:

1. Ensure that your Java application is using Java 8 or later, as specified in the `build.gradle` file.
2. Use the `javax.net.ssl` package to configure the SSL/TLS settings in your Java application.
3. Set the desired encryption cipher by specifying it in the SSL/TLS configuration.

Here is an example of how to configure SSL/TLS settings and set the desired encryption cipher:

```java
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
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
```

## What's next

* Learn to send a request to Telesign with code with one of our [tutorials](https://developer.telesign.com/enterprise/docs/tutorials).
* Browse our [Developer Portal](https://developer.telesign.com) for tutorials, how-to guides, reference content, and more.
* Check out our [sample code](https://github.com/TeleSign/sample_code) on GitHub.
