package com.telesign.enterprise.example.phoneid_number_deactivation;

import com.telesign.RestClient;
import com.telesign.enterprise.PhoneIdClient;

public class CheckPhoneNumberDeactivated {

    public static void main(String[] args) {

        String customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        String apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";

        String phoneNumber = "phone_number";
        String ucid = "ATCK";

        try {

            PhoneIdClient phoneIdClient = new PhoneIdClient(customerId, apiKey);
            RestClient.TelesignResponse telesignResponse = phoneIdClient.numberDeactivation(phoneNumber, ucid, null);

            if (telesignResponse.ok) {
                if (!telesignResponse.json.getAsJsonObject("number_deactivation").get("last_deactivated").isJsonNull()) {
                    System.out.println(String.format("Phone number %s was last deactivated %s.",
                            telesignResponse.json.getAsJsonObject("number_deactivation").get("number").getAsString(),
                            telesignResponse.json.getAsJsonObject("number_deactivation").get("last_deactivated").getAsString()));
                } else {
                    System.out.println(String.format("Phone number %s has not been deactivated.",
                            telesignResponse.json.getAsJsonObject("number_deactivation").get("number").getAsString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}