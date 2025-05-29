package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OmniVerifyClientTest extends TestCase {

    private MockWebServer mockServer;

    private String customerId;
    private String apiKey;

    public void setUp() throws Exception {
        super.setUp();

        this.customerId = System.getenv("CUSTOMER_ID");
        this.apiKey = System.getenv("API_KEY");

        if (this.customerId == null) this.customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        if (this.apiKey == null)
            this.apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";

        this.mockServer = new MockWebServer();
        this.mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testOmniVerifyClientCreateVerificationProcessWithoutParams() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        OmniVerifyClient client = new OmniVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String phone_number = "11234567890";

        client.createVerificationProcess(phone_number, null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("{\"recipient\":{\"phone_number\":\"%s\"},\"verification_policy\":[{\"method\":\"sms\"}]}", phone_number);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/verification", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/json", request.getHeader("Content-Type"));
    }

    public void testOmniVerifyClientCreateVerificationProcessWithParams() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        OmniVerifyClient client = new OmniVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String phone_number = "11234567890";

        HashMap<String, Object> params = new HashMap<>();

        List<Object> infoVerificationPolicy = new ArrayList<>();

        HashMap<String, String> infoMethod1 = new HashMap<>();
        infoMethod1.put("method", "whatsapp");
        infoVerificationPolicy.add(infoMethod1);

        HashMap<String, String> infoMethod2 = new HashMap<>();
        infoMethod2.put("method", "push");
        infoVerificationPolicy.add(infoMethod2);

        params.put("verification_policy", infoVerificationPolicy);

        client.createVerificationProcess(phone_number, params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        String bodyExpected = String.format("{\"recipient\":{\"phone_number\":\"%s\"},\"verification_policy\":[{\"method\":\"whatsapp\"},{\"method\":\"push\"}]}", phone_number);
        assertEquals("body is not as expected", bodyExpected, request.getBody().readUtf8());
        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/verification", request.getPath());
        assertEquals("Content-Type header is not as expected", "application/json", request.getHeader("Content-Type"));
    }

    public void testOmniVerifyClientRetrieveVerificationProcessWithoutParams() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        OmniVerifyClient client = new OmniVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "referenceId";

        client.getVerificationProcess(referenceId, null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", String.format("/verification/%s", referenceId), request.getPath());
    }

    public void testOmniVerifyClientRetrieveVerificationProcessWithParams() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        OmniVerifyClient client = new OmniVerifyClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "referenceId";

        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        client.getVerificationProcess(referenceId, params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", String.format("/verification/%s?param1=value1", referenceId), request.getPath());
    }

}