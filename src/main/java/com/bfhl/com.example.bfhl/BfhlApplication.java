package com.example.bfhl;

import com.example.bfhl.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BfhlApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfhlApplication.class, args);
    }

    // This runs after the app starts
    @Bean
    public CommandLineRunner run(WebhookService webhookService) {
        return args -> {
            webhookService.execute();  // Run the logic we created
        };
    }
}
