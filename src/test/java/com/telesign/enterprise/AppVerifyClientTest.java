package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AppVerifyClientTest extends TestCase {

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
            this.apiKey = "ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";
        }

        this.mockServer = new MockWebServer();
        this.mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testSendCode() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        AppVerifyClient client = new AppVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        HashMap<String, String> params = new HashMap<>();

        String phoneNumber = "11234567890";

        client.sendCode(phoneNumber, params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("phone_number=%s", phoneNumber);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/auto/voice/initiate", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded", request.getHeader("Content-Type"));
    }

    public void testEndCall() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        AppVerifyClient client = new AppVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "0123456789ABCDEF0123456789ABCDEF";

        client.endCall(referenceId, "");

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("reference_id=%s", referenceId);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/auto/voice/finalize", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded", request.getHeader("Content-Type"));
    }

    public void testReportCallerId() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        AppVerifyClient client = new AppVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "0123456789ABCDEF0123456789ABCDEF";
        String unknownCallerid = "test";

        client.reportCallerId(referenceId, unknownCallerid,"");

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("reference_id=%s&unknown_caller_id=%s", referenceId, unknownCallerid);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/auto/voice/finalize/callerid", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded", request.getHeader("Content-Type"));
    }

    public void testReportTimeout() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        AppVerifyClient client = new AppVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "0123456789ABCDEF0123456789ABCDEF";

        client.reportTimeout(referenceId);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("reference_id=%s", referenceId);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/verify/auto/voice/finalize/timeout", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded", request.getHeader("Content-Type"));
    }

    public void testStatus() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        AppVerifyClient client = new AppVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String reference_id = "0123456789ABCDEF0123456789ABCDEF";

        client.getStatus(reference_id);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", String.format("/v1/verify/auto/voice/%s", reference_id), request.getPath());
    }

}