package phoneid_number_deactivation;

import com.telesign.RestClient;
import com.telesign.enterprise.PhoneIdClient;

public class App {

    public static void main(String[] args) {

        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "EXAMPLE----ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==");

        String phoneNumber = System.getenv().getOrDefault("PHONE_NUMBER", "123456789");// Your test phone number with no special characters or spaces.
       
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