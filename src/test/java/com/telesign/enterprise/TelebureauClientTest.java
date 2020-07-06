package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TelebureauClientTest extends TestCase {

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

    public void testTelebureauConstructorDefault() throws Exception {
        TelebureauClient client = new TelebureauClient(customerId, apiKey);
        assertNotNull(client);
    }

    public void testTelebureauConstructorFull() throws Exception {
        TelebureauClient client = new TelebureauClient(
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

    public void testTelebureauCreateEvent() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));
        TelebureauClient client = new TelebureauClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.createEvent(
                "18005555555",
                "fraudType",
                "occurredAt",
                null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/telebureau/event", request.getPath());
        assertEquals("body is not as expected",
                "occurred_at=occurredAt&fraud_type=fraudType&phone_number=18005555555",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testTelebureauRetrieveEvent() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));
        TelebureauClient client = new TelebureauClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.retrieveEvent("FakeReferenceId",null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/telebureau/event/FakeReferenceId", request.getPath());
        assertEquals("body is not as expected",
                "",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testTelebureauDeleteEvent() throws Exception {
        this.mockServer.enqueue(new MockResponse().setBody("{}"));
        TelebureauClient client = new TelebureauClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.deleteEvent("FakeReferenceId",null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "DELETE", request.getMethod());
        assertEquals("path is not as expected", "/v1/telebureau/event/FakeReferenceId", request.getPath());
        assertEquals("body is not as expected",
                "",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }
}


