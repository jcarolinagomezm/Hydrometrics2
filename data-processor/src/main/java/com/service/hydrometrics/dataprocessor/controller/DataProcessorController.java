package com.service.hydrometrics.dataprocessor.controller;

import com.service.hydrometrics.dataprocessor.models.WeatherDataMapper;
import com.service.hydrometrics.dataprocessor.service.DataProcess;
import com.service.hydrometrics.dataprocessor.service.Persisten;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        log.info("Guardado de datos completado");
        return "Procesado de datos realizado";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Servicio en funcionamiento";
    }
}
