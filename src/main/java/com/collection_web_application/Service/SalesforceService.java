package com.collection_web_application.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SalesforceService {

    public String createSalesforceAccountAndContact(String accessToken, String name, String email) throws IOException {
        String accountJson = "{\"Name\":\"" + name + "\"}";
        String accountUrl = "https://itransition70-dev-ed.develop.my.salesforce.com/services/data/v52.0/sobjects/Account"; //itransition70-dev-ed.develop.my.salesforce

        HttpURLConnection connection = (HttpURLConnection) new URL(accountUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = accountJson.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            // Get the Account ID from the response and create a Contact linked to this Account
            // Implement the logic here
            return "Account and Contact created successfully!";
        } else {
            return "Failed to create Account. Response Code: " + responseCode;
        }
    }
}
