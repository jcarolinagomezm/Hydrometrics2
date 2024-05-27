package com.service.hydrometrics.dataprocessor.service;

import com.service.hydrometrics.dataprocessor.entity.Alert;
import com.service.hydrometrics.dataprocessor.entity.WeatherData;
import com.service.hydrometrics.dataprocessor.enums.ActionLog;
import com.service.hydrometrics.dataprocessor.utils.UtilsMethods;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Persisten {

    private final EntityManager entityManager;
    private final DataProcess dataProcess;

    @Transactional
    public List<Alert> batchSaveWeatherData(List<WeatherData> weatherDataList, String username) {
        List<Alert> alerts = new ArrayList<>();
        for (int i = 0; i < weatherDataList.size(); i++) {
            WeatherData weatherData = weatherDataList.get(i);
            alerts.addAll(dataProcess.analizedData(weatherData));
            entityManager.persist(weatherData);
            if (i % 50 == 0) { // Cada 50 registros, realiza un flush y clear.
                entityManager.flush();
                entityManager.clear();
                UtilsMethods.generatePersistentLogger("WeatherData", ActionLog.CREATE, username);
            }
        }
        entityManager.flush(); // Asegura que los datos restantes se guarden.
        entityManager.clear();
        UtilsMethods.generatePersistentLogger("WeatherData", ActionLog.CREATE, username);
        return alerts;
    }

    @Transactional
    public void batchSaveAlerts(List<Alert> alerts, String username) {
        for (int i = 0; i < alerts.size(); i++) {
            Alert alert = alerts.get(i);
            entityManager.persist(alert);
            if (i % 50 == 0) { // Cada 50 registros, realiza un flush y clear.
                entityManager.flush();
                entityManager.clear();
                UtilsMethods.generatePersistentLogger("Alert", ActionLog.CREATE, username);
            }
        }
        entityManager.flush(); // Asegura que los datos restantes se guarden.
        entityManager.clear();
        UtilsMethods.generatePersistentLogger("Alert", ActionLog.CREATE, username);
    }
}
