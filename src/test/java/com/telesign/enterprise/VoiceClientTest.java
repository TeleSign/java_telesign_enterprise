package com.telesign.enterprise;

import junit.framework.TestCase;
import okhttp3.mockwebserver.MockWebServer;

import java.util.Map;

public class VoiceClientTest extends TestCase {

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

    public void testInheritedMethodsVoice() throws Exception {
                VoiceClient client = new VoiceClient(this.customerId, this.apiKey);

        assertTrue(client instanceof com.telesign.VoiceClient);
        assertEquals(com.telesign.VoiceClient.class,
                client.getClass().getMethod("call",
                        String.class, String.class, String.class, Map.class)
                        .getDeclaringClass());
        assertEquals(com.telesign.VoiceClient.class,
                client.getClass().getMethod("status", String.class, Map.class)
                        .getDeclaringClass());
    }
}
