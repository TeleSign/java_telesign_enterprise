package appverify;

import com.telesign.RestClient;
import com.telesign.enterprise.AppVerifyClient;

import java.util.HashMap;

public class SendCodeAppVerify {

    public static void main(String[] args) {

        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "EXAMPLE----ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==");
        String phoneNumber = System.getenv().getOrDefault("PHONE_NUMBER", "573206419532");

        HashMap<String, String> params = new HashMap<>();

        try {
            AppVerifyClient appVerifyClient = new AppVerifyClient(customerId, apiKey);
            RestClient.TelesignResponse telesignResponse = appVerifyClient.sendCode(phoneNumber, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}