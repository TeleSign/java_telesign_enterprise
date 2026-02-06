package com.telesign.enterprise.example.score;

import com.telesign.RestClient;
import com.telesign.enterprise.ScoreClient;

public class CheckPhoneNumberRiskLevel {

    public static void main(String[] args) {

        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==");
        String phoneNumber = System.getenv().getOrDefault("PHONE_NUMBER", "11234567890"); // Your test phone number with no special characters or spaces.
        String accountLifecycleEvent = "create";

        try {
            ScoreClient scoreClient = new ScoreClient(customerId, apiKey);
            RestClient.TelesignResponse telesignResponse = scoreClient.score(phoneNumber, accountLifecycleEvent, null);

            if (telesignResponse.ok) {
                System.out.println(String.format(
                    "Phone number %s has a '%s' risk level and the recommendation is to '%s' the transaction.",
                    phoneNumber,
                    telesignResponse.json.getAsJsonObject("risk").get("level").getAsString(),
                    telesignResponse.json.getAsJsonObject("risk").get("recommendation").getAsString()));
            } else {
                System.out.println("Request failed with status: " + telesignResponse.statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
