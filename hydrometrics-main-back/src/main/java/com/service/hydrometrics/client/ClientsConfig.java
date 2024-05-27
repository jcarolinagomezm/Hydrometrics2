package com.service.hydrometrics.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientsConfig {

    @Value("${data.processor.url}")
    private String baseUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        System.out.println("baseURL -> " + baseUrl);
        return WebClient.builder().baseUrl(baseUrl);
    }
}
