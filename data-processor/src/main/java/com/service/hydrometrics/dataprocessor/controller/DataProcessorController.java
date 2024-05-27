package com.service.hydrometrics.dataprocessor.controller;

import com.service.hydrometrics.dataprocessor.models.WeatherDataMapper;
import com.service.hydrometrics.dataprocessor.service.DataProcess;
import com.service.hydrometrics.dataprocessor.service.Persisten;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DataProcessorController {

    private final DataProcess dataProcess;
    private final Persisten persisten;
    @Getter
    private String username;

    @PostMapping("/process")
    public String process(@RequestBody List<WeatherDataMapper> weatherDataMappers, @RequestParam String username) {
        this.username = username;
        var weatherDatas = dataProcess.WeatherDataMapperToEntity(weatherDataMappers);
        var alerts = persisten.batchSaveWeatherData(weatherDatas, username);
        persisten.batchSaveAlerts(alerts, username);
        return "Procesado de datos realizado";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Servicio en funcionamiento";
    }
}
