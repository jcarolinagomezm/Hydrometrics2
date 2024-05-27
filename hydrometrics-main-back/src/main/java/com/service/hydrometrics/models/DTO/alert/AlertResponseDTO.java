package com.service.hydrometrics.models.DTO.alert;

import com.service.hydrometrics.models.DB.entity.Alert;
import com.service.hydrometrics.models.DB.entity.WeatherData;
import com.service.hydrometrics.models.DTO.weatherData.WeatherDataDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AlertResponseDTO implements Serializable {

    //Campo que se detecto que esta mal
    @NotNull
    private final long alertId;
    @NotEmpty
    private final String dataCamp;
    //Datos del WeatherDate
    @NotNull
    @NotEmpty
    private final WeatherDataDTO weatherDataDTO;

    public AlertResponseDTO(Alert alert, WeatherData weatherData) {
        this.alertId = alert.getId();
        this.dataCamp = alert.getDataCamp().toString();
        this.weatherDataDTO = new WeatherDataDTO(weatherData);
    }
}
