package com.service.hydrometrics.services.Impl;

import com.service.hydrometrics.utils.UtilsMethods;
import com.service.hydrometrics.models.DB.entity.Alert;
import com.service.hydrometrics.models.DB.entity.Prediction;
import com.service.hydrometrics.models.DB.entity.WeatherData;
import com.service.hydrometrics.models.DTO.alert.AlertResponseDTO;
import com.service.hydrometrics.models.DTO.alert.ConfirmAlertDTO;
import com.service.hydrometrics.models.DTO.alert.CorrelationRequestDTO;
import com.service.hydrometrics.models.DTO.alert.CorrelationResponseDTO;
import com.service.hydrometrics.models.enums.ActionLog;
import com.service.hydrometrics.models.enums.DataCamp;
import com.service.hydrometrics.models.enums.Status;
import com.service.hydrometrics.repository.AlertRepository;
import com.service.hydrometrics.services.IAlertService;
import com.service.hydrometrics.services.IPredictionService;
import com.service.hydrometrics.services.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AlertService implements IAlertService {

    private final AlertRepository repo;
    private final IWeatherService weatherService;
    private final IPredictionService predictionService;

    @Override
    @Transactional(readOnly = true)
    public Optional<Alert> getAlert(Long alertId) {
        return repo.findById(alertId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertResponseDTO> getAlertsToReview() {
        List<AlertResponseDTO> responseDTOS = new ArrayList<>();
        List<Alert> alerts = repo.findTop100ByStatusIs(Status.UNCHECKED);
        for (Alert alert : alerts) {
            Optional<WeatherData> oWeatherData = weatherService.getWeatherData(alert.getWeatherData().getId());
            if (oWeatherData.isPresent()) {
                WeatherData weatherData = oWeatherData.get();
                AlertResponseDTO response = new AlertResponseDTO(alert, weatherData);
                responseDTOS.add(response);
            }
        }
        return responseDTOS;
    }

    @Transactional
    @Override
    public boolean processConfirmAlert(ConfirmAlertDTO confirm) {
        Optional<Alert> alert = getAlert(confirm.alertId());
        if (alert.isEmpty()) return false;
        Optional<WeatherData> weatherData = weatherService.getWeatherData(alert.get().getWeatherData().getId());
        if (weatherData.isEmpty()) return false;
        switch (alert.get().getDataCamp()) {
            case TEMPERATURA -> weatherData.get().setTemperature(confirm.prediction());
            case VELOCIDAD_VIENTO -> weatherData.get().setWindSpeed(confirm.prediction());
            case PRECIPITACION -> weatherData.get().setPrecipitation(confirm.prediction());
            case HUMEDAD_RELATIVA -> weatherData.get().setRelativeHumidity(confirm.prediction());
            case DIRECCION_VIENTO -> weatherData.get().setWindDirection(confirm.prediction());
            case RADIACION_SOLAR -> weatherData.get().setSolarRadiation(confirm.prediction());
        }
        Prediction prediction = new Prediction(confirm.prediction(), alert.get().getDataCamp(), weatherData.orElse(null), alert.orElse(null));
        weatherService.update(weatherData.get());
        predictionService.save(prediction);
        alert.get().setStatus(Status.CHECKED);
        repo.save(alert.get());
        UtilsMethods.generatePersistentLogger("Alert" , ActionLog.UPDATE);
        return true;
    }

    @Override
    public List<CorrelationResponseDTO> getCorrelationAlert(CorrelationRequestDTO correlationRequest) {
        DataCamp predictionVariable = DataCamp.valueOf(correlationRequest.getDataCamp().toUpperCase());
        Integer predictionValue = correlationRequest.getPredictionValue();
        List<CorrelationResponseDTO> correlationResponseDTOS = new ArrayList<>();
        Map<DataCamp, List<DataCamp>> mapCorrelation = Map.of(
                DataCamp.HUMEDAD_RELATIVA,
                List.of(DataCamp.TEMPERATURA, DataCamp.PRECIPITACION),
                DataCamp.TEMPERATURA,
                List.of(DataCamp.HUMEDAD_RELATIVA, DataCamp.RADIACION_SOLAR),
                DataCamp.PRECIPITACION,
                List.of(DataCamp.HUMEDAD_RELATIVA),
                DataCamp.RADIACION_SOLAR,
                List.of(DataCamp.TEMPERATURA));
        List<DataCamp> correlatedVariables = mapCorrelation.get(predictionVariable);
        if (correlatedVariables != null && !correlatedVariables.isEmpty()) {
            correlatedVariables.forEach(v -> {
                Integer promedioVariable = weatherService.averageVariableRange(predictionVariable, v, predictionValue);
                CorrelationResponseDTO response = new CorrelationResponseDTO(v.toString().toLowerCase(), promedioVariable);
                correlationResponseDTOS.add(response);
            });
        }
        return correlationResponseDTOS;
    }


}
