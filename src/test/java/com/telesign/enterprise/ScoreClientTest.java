package com.telesign.enterprise;

import junit.framework.TestCase;

public class ScoreClientTest extends TestCase {

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
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInheritedMethodsScore() throws Exception {
        ScoreClient client = new ScoreClient(this.customerId, this.apiKey);

        assertTrue(client instanceof com.telesign.ScoreClient);
        assertEquals(com.telesign.ScoreClient.class,
                client.getClass().getMethod("score", String.class, String.class, java.util.Map.class)
                        .getDeclaringClass());
    }
}
