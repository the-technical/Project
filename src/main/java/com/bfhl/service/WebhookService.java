package com.example.bfhl.service;

import com.example.bfhl.model.WebhookRequest;
import com.example.bfhl.model.WebhookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    @Value("${webhook.api.url}")
    private String webhookUrl;

    @Value("${name}")
    private String name;

    @Value("${regNo}")
    private String regNo;

    @Value("${email}")
    private String email;

    public void execute() {
        RestTemplate restTemplate = new RestTemplate();

        // Create the request body
        WebhookRequest request = new WebhookRequest(name, regNo, email);

        // Make the POST call to generate webhook
        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                webhookUrl, request, WebhookResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            WebhookResponse webhookResponse = response.getBody();
            System.out.println("✅ Webhook URL: " + webhookResponse.getWebhookUrl());
            System.out.println("✅ Access Token: " + webhookResponse.getAccessToken());

            // You will manually solve the SQL question here (from Google Drive)
            String finalQuery = "YOUR_SQL_QUERY_HERE"; // Replace with actual SQL

            // Submit the query
            submitFinalQuery(restTemplate, webhookResponse.getWebhookUrl(), webhookResponse.getAccessToken(), finalQuery);
        } else {
            System.out.println("❌ Failed to generate webhook");
        }
    }

    private void submitFinalQuery(RestTemplate restTemplate, String url, String token, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println("✅ Submission Status: " + response.getStatusCode());
        System.out.println("✅ Response: " + response.getBody());
    }
}
