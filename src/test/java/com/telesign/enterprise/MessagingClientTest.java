package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MessagingClientTest extends TestCase {

    private MockWebServer mockServer;

    private String customerId;
    private String apiKey;


    public void setUp() throws Exception {
        super.setUp();

        this.customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        this.apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";

        this.mockServer = new MockWebServer();
        this.mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testMessagingConstructorDefault() throws Exception {
        MessagingClient client = new MessagingClient(customerId, apiKey);
        assertNotNull(client);
    }

    public void testMessagingConstructorFull() throws Exception {
        MessagingClient client = new MessagingClient(
                customerId,
                apiKey,
                "restEndpoint",
                0,
                0,
                0,
                null,
                "",
                ""
        );
        assertNotNull(client);
    }

    public void testMessaging() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));
        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.message("18005555555", "Test Message", "ARN", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/messaging", request.getPath());
        assertEquals("body is not as expected",
                "phone_number=18005555555&message_type=ARN&message=Test%20Message",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }
}


