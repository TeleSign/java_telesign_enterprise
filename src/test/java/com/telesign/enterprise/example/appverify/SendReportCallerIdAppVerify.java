package com.telesign.enterprise.example.appverify;

import com.telesign.RestClient;
import com.telesign.enterprise.AppVerifyClient;

import java.util.HashMap;

public class SendReportCallerIdAppVerify {

    public static void main(String[] args) {

        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "EXAMPLE----ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==");
        String referenceId = System.getenv().getOrDefault("REFERENCE_ID", "0123456789ABCDEF0123456789ABCDEF");

        try {
            AppVerifyClient appVerifyClient = new AppVerifyClient(customerId, apiKey);

            String unknownCallerid = "test";
            String assignedCustomerId = "";

            RestClient.TelesignResponse telesignResponseReportCallerId = appVerifyClient.reportCallerId(referenceId, unknownCallerid, assignedCustomerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}