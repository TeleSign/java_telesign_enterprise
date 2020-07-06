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

        this.customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        this.apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";

        this.mockServer = new MockWebServer();
        this.mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testVerifyConstructorDefault() throws Exception {
        VerifyClient client = new VerifyClient(customerId, apiKey);
        assertNotNull(client);
    }

    public void testVerifyConstructorFull() throws Exception {
        VerifyClient client = new VerifyClient(
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

    public void testVerifySms() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.sms("18005555555", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/sms", request.getPath());
        assertEquals("body is not as expected", "phone_number=18005555555",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testVerifyVoice() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.voice("18005555555", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/call", request.getPath());
        assertEquals("body is not as expected", "phone_number=18005555555",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testVerifySmart() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.smart("18005555555", "UCID", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/smart", request.getPath());
        assertEquals("body is not as expected", "phone_number=18005555555&ucid=UCID",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testVerifyStatus() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.status("FakeReferenceId",null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/FakeReferenceId", request.getPath());
        assertEquals("body is not as expected", "",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testVerifyCompletion() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        VerifyClient client = new VerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.completion("FakeReferenceId",null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "PUT", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/completion/FakeReferenceId", request.getPath());
        assertEquals("body is not as expected", "",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }
}


