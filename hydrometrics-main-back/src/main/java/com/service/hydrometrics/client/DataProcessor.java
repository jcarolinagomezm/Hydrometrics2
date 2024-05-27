package com.service.hydrometrics.client;

import com.service.hydrometrics.utils.WeatherDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataProcessor {

    private final WebClient.Builder webClientBuilder;

    public void sendWeatherDataMappers(List<WeatherDataMapper> weatherDataMappers, String username) {
        WebClient webClient = webClientBuilder.build();
        webClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("/process")
                        .queryParam("username", username)
                        .build())
                .body(Mono.just(weatherDataMappers), new ParameterizedTypeReference<List<WeatherDataMapper>>() {
                })
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("Error during WebClient request: ", error))
                .subscribe(log::info);
    }
}
