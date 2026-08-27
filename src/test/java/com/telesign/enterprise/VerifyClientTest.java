package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.HashMap;
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

    public void testSms() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId, this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.sms("11234567890", new HashMap<String, String>());

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("POST", request.getMethod());
        assertEquals("/v1/verify/sms", request.getPath());
        assertEquals("application/x-www-form-urlencoded", request.getHeader("Content-Type"));
        assertTrue(request.getBody().readUtf8().contains("phone_number=11234567890"));
    }

    public void testVoice() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId, this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.voice("11234567890", new HashMap<String, String>());

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("POST", request.getMethod());
        assertEquals("/v1/verify/call", request.getPath());
        assertEquals("application/x-www-form-urlencoded", request.getHeader("Content-Type"));
        assertTrue(request.getBody().readUtf8().contains("phone_number=11234567890"));
    }

    public void testSmart() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId, this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.smart("11234567890", "12345", new HashMap<String, String>());

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("POST", request.getMethod());
        assertEquals("/v1/verify/smart", request.getPath());
        assertEquals("application/x-www-form-urlencoded", request.getHeader("Content-Type"));
        String body = request.getBody().readUtf8();
        assertTrue(body.contains("phone_number=11234567890"));
        assertTrue(body.contains("ucid=12345"));
    }

    public void testStatus() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId, this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.status("0123456789ABCDEF0123456789ABCDEF", new HashMap<String, String>());

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("GET", request.getMethod());
        assertEquals("/v1/verify/0123456789ABCDEF0123456789ABCDEF", request.getPath());
        assertEquals("", request.getBody().readUtf8());
    }

    public void testCompletion() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId, this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.completion("0123456789ABCDEF0123456789ABCDEF", new HashMap<String, String>());

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("PUT", request.getMethod());
        assertEquals("/v1/verify/completion/0123456789ABCDEF0123456789ABCDEF", request.getPath());
    }

    public void testCreateVerificationProcess() throws Exception {
        VerifyClient client = new VerifyClient(this.customerId, this.apiKey);

        assertNotNull(client.createVerificationProcess("11234567890", new HashMap<String, Object>()));
    }
}