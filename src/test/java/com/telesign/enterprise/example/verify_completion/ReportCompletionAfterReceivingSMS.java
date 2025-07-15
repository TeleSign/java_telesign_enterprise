package verify_completion;

import com.telesign.RestClient;
import com.telesign.Util;
import com.telesign.enterprise.VerifyClient;

import java.util.HashMap;
import java.util.Scanner;

public class ReportCompletionAfterReceivingSMS {

    public static void main(String[] args) {

        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "EXAMPLE----ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==");

        String phoneNumber = System.getenv().getOrDefault("PHONE_NUMBER", "123456789");// Your test phone number with no special characters or spaces.

        String verifyCode = Util.randomWithNDigits(5);

        HashMap<String, String> params = new HashMap<>();
        params.put("verify_code", verifyCode);

        try {
            VerifyClient verifyClient = new VerifyClient(customerId, apiKey);
            RestClient.TelesignResponse telesignResponse = verifyClient.sms(phoneNumber, params);

            String referenceId = telesignResponse.json.get("reference_id").getAsString();

            Scanner s = new Scanner(System.in);
            System.out.println("Please enter your verification code:");
            String code = s.next();

            if (verifyCode.equalsIgnoreCase(code)) {
                System.out.println("Your code is correct.");

                telesignResponse = verifyClient.completion(referenceId, null);

                if (telesignResponse.ok
                        && telesignResponse.json.getAsJsonObject("status").get("code").getAsInt() == 1900) {
                    System.out.println("Completion successfully reported.");
                } else {
                    System.out.println("Error reporting completion.");
                }

            } else {
                System.out.println("Your code is incorrect.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
