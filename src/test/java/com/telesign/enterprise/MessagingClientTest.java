package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MessagingClientTest extends TestCase {

    private MockWebServer mockServer;

    private String customerId;
    private String apiKey;
    private String phoneNumber;

    private SimpleDateFormat rfc2616 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");


    public void setUp() throws Exception {
        super.setUp();

        this.customerId = System.getenv("CUSTOMER_ID");
        this.apiKey = System.getenv("API_KEY");
        this.phoneNumber = System.getenv("PHONE_NUMBER");

        if (this.customerId == null) {
            this.customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        }
        if (this.apiKey == null) {
            this.apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";
        }
        if (this.phoneNumber == null) {
            this.phoneNumber = "11234567890";
        }

        mockServer = new MockWebServer();
        mockServer.start();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        this.mockServer.shutdown();
    }

    public void testMessagingClientConstructorMinimal() throws Exception {
        com.telesign.enterprise.MessagingClient client = new MessagingClient(customerId, apiKey);
        assertNotNull(client);
    }


    public void testMessagingClientConstructorFull() throws Exception {
        MessagingClient client = new MessagingClient(customerId,
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

    public void testMessagingWithParams() throws Exception {

        HashMap<String, String> params = new HashMap<String, String>() {{
            put("originating_ip", "127.0.0.1");
            put("account_lifecycle_event", "create");
        }};

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.message("18005555555", "Test Message Content", "MKT", params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/messaging", request.getPath());
        assertEquals("body is not as expected",
                "originating_ip=127.0.0.1&account_lifecycle_event=create&phone_number=18005555555&" +
                        "message_type=MKT&message=Test+Message+Content",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testMessagingNullParams() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.message("18005555555", "Test Message Content", "ARN", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/messaging", request.getPath());
        assertEquals("body is not as expected", "phone_number=18005555555&message_type=ARN&message=Test+Message+Content",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/x-www-form-urlencoded",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testOmniMessagingWithOptionalParams() throws Exception {

        HashMap<String, Object> params = new HashMap<String, Object>() {{
            put("message", new HashMap<String, Object >() {{
                put("sms", new HashMap<String, Object >() {{
                    put("template", "text");
                    put("parameters", new HashMap<String, String >() {{
                        put("text", "Test Message Content");
                    }});
                }});
            }});
            put("recipient", new HashMap<String, String >() {{
                put("phone_number", "18005555555");
            }});
            put("channels", new ArrayList<HashMap<String,Object>>() {{
                add(new HashMap<String,Object>() {{
                    put("channel", "sms");
                    put("fallback_time", 300);
                }});
            }});
            put("message_type", "MKT");
            put("originating_ip", "127.0.0.1");
            put("account_lifecycle_event", "create");
        }};

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.omniMessage(params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel", request.getPath());
        assertEquals("body is not as expected",
                "{\"channels\":[{\"channel\":\"sms\",\"fallback_time\":300}],\"originating_ip\":\"127.0.0.1\",\"account_lifecycle_event\":\"create\",\"recipient\":{\"phone_number\":\"18005555555\"},\"message_type\":\"MKT\",\"message\":{\"sms\":{\"template\":\"text\",\"parameters\":{\"text\":\"Test Message Content\"}}}}",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/json",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testOmniMessagingWithoutOptionalParams() throws Exception {

        HashMap<String, Object> params = new HashMap<String,Object>() {{
            put("message", new HashMap<String, Object >() {{
                put("sms", new HashMap<String, Object >() {{
                    put("template", "text");
                    put("parameters", new HashMap<String, String >() {{
                            put("text", "Test Message Content");
                        }});
                }});
            }});
            put("recipient", new HashMap<String, String >() {{
                    put("phone_number", "18005555555");
            }});
            put("channels", new ArrayList<HashMap<String,Object>>() {{
                add(new HashMap<String,Object>() {{
                    put("channel", "sms");
                    put("fallback_time", 300);
                }});
            }});
            put("message_type", "ARN");
        }};
        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.omniMessage(params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel", request.getPath());
        assertEquals("body is not as expected", "{\"channels\":[{\"channel\":\"sms\",\"fallback_time\":300}],\"recipient\":{\"phone_number\":\"18005555555\"},\"message_type\":\"ARN\",\"message\":{\"sms\":{\"template\":\"text\",\"parameters\":{\"text\":\"Test Message Content\"}}}}",
                request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/json",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testMessagingStatus() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.status("FakeReferenceId", null);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/messaging/FakeReferenceId", request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testCheckChannelCapability() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String channel = "rcs";
        client.checkChannelCapability(channel, phoneNumber);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/capability/" + channel + "/" + this.phoneNumber, request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testCheckCapabilityRBMAgent() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.checkCapabilityRBMAgent(phoneNumber, "my_rbm_id");

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/capability/rcs/" + this.phoneNumber + "/my_rbm_id", request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }


    public void testGetMsgStatus() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "FakeReferenceId";
        String verifyCode = "123456";

        client.getMsgStatus(referenceId, verifyCode);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/" + referenceId + "?verify_code=" + verifyCode, request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testGetMsgStatusWithoutVerifyCode() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String referenceId = "FakeReferenceId";

        client.getMsgStatus(referenceId);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/" + referenceId, request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testCreateMsgTemplate() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        Map<String, Object> params = new HashMap<>();

        params.put("type", "standard");
        params.put("channel", "whatsapp");
        params.put("name", "wa_bogo");

        // body map
        Map<String, Object> body = new HashMap<>();
        body.put("type", "text");
        body.put("text", "Buy one get one free!");

        // content item
        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("body", body);

        // content list
        List<Map<String, Object>> content = new ArrayList<>();
        content.add(contentItem);

        params.put("content", content);

        client.createMsgTemplate(params);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "POST", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/templates", request.getPath());
        assertEquals("body is not as expected", "{\"channel\":\"whatsapp\",\"name\":\"wa_bogo\",\"type\":\"standard\",\"content\":[{\"body\":{\"text\":\"Buy one get one free!\",\"type\":\"text\"}}]}", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "application/json",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testGetMsgTemplate() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String channel = "whatsapp";
        String templateName = "wa_bogo";

        client.getMsgTemplate(channel, templateName);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/templates/" + channel + "/" + templateName, request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testGetAllMsgTemplates() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        client.getAllMsgTemplates();

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "GET", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/templates", request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

    public void testDeleteMsgTemplate() throws Exception {

        this.mockServer.enqueue(new MockResponse().setBody("{}"));

        MessagingClient client = new MessagingClient(this.customerId,
                this.apiKey,
                this.mockServer.url("").toString().replaceAll("/$", ""));

        String channel = "whatsapp";
        String templateName = "wa_bogo";

        client.deleteMsgTemplate(channel, templateName);

        RecordedRequest request = this.mockServer.takeRequest(1, TimeUnit.SECONDS);

        assertEquals("method is not as expected", "DELETE", request.getMethod());
        assertEquals("path is not as expected", "/v1/omnichannel/templates/" + channel + "/" + templateName, request.getPath());
        assertEquals("body is not as expected", "", request.getBody().readUtf8());
        assertEquals("Content-Type header is not as expected", "",
                request.getHeader("Content-Type"));
        assertEquals("x-ts-auth-method header is not as expected", "HMAC-SHA256",
                request.getHeader("x-ts-auth-method"));
    }

}


