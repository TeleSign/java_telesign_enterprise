package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyClientTest extends TestCase {

    private MockWebServer mockServer;

    private String customerId;
    private String apiKey;


    public void setUp() throws Exception {
        super.setUp();

        this.customerId = System.getenv("CUSTOMER_ID");
        this.apiKey = System.getenv("API_KEY");

        if (this.customerId == null) {
            this.customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        }
        if (this.apiKey == null) {
            this.apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";
        }

        this.mockServer = new MockWebServer();
        this.mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testVerifyClientConstructorMininal() throws Exception {
        VerifyClient client = new VerifyClient(customerId, apiKey);
        assertNotNull(client);
    }

    public void testVerifyClientConstructorFull() throws Exception {
        VerifyClient client = new VerifyClient(customerId,
                apiKey,
                "rest endpoint",
                0,
                0,
                0,
                null,
                "",
                "");
        assertNotNull(client);
    }

    

    

    

    

    public void testInheritedMethodsVerify() throws Exception {
        VerifyClient client = new VerifyClient(this.customerId, this.apiKey);

        assertTrue(client instanceof VerifyClient);
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("sms", String.class, Map.class).getDeclaringClass());
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("voice", String.class, Map.class).getDeclaringClass());
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("smart", String.class, String.class, Map.class)
                        .getDeclaringClass());
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("status", String.class, Map.class).getDeclaringClass());
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("completion", String.class, Map.class).getDeclaringClass());
        assertEquals(VerifyClient.class,
                client.getClass().getMethod("createVerificationProcess", String.class, HashMap.class)
                        .getDeclaringClass());
    }
}