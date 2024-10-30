package com.telesign.enterprise.example.verify_sms;

import com.telesign.RestClient;
import com.telesign.enterprise.VerifyClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendSMSWithNewEndpoint {

    public static void main(String[] args) {

        String customerId = "FFFFFFFF-EEEE-DDDD-1234-AB1234567890";
        String apiKey = "EXAMPLE----TE8sTgg45yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==";

        String phoneNumber = "phone_number";

        HashMap<String, Object> params = new HashMap<>();

        params.put("security_factor", "5724433");

        List<Object> infoVerificationPolicy = new ArrayList<>();

        HashMap<String, String> infoMethod1 = new HashMap<>();
        infoMethod1.put("method", "sms");
        infoVerificationPolicy.add(infoMethod1);

        HashMap<String, String> infoMethod2 = new HashMap<>();
        infoMethod2.put("method", "push");
        infoVerificationPolicy.add(infoMethod2);

        HashMap<String, String> infoMethod3 = new HashMap<>();
        infoMethod3.put("method", "whatsapp");
        infoVerificationPolicy.add(infoMethod3);

        params.put("verification_policy", infoVerificationPolicy);

        try {
            VerifyClient verifyClient = new VerifyClient(customerId, apiKey);
            // RestClient.TelesignResponse telesignResponse = verifyClient.sms(phoneNumber, null);
            // RestClient.TelesignResponse telesignResponse = verifyClient.createVerificationProcess(phoneNumber, params);
            RestClient.TelesignResponse telesignResponse = verifyClient.createVerificationProcess(phoneNumber, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}