package com.service.hydrometrics.models.DTO.weatherData;

import com.service.hydrometrics.models.DB.entity.WeatherData;
import com.service.hydrometrics.utils.UtilsMethods;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link WeatherData}
 */
@Getter
public class WeatherDataReportDTO implements Serializable {
    private final Double precipitation;
    private final Double relative_humidity;
    private final Double solar_radiation;
    private final Double temperature;
    private final Double wind_direction;
    private final Double wind_speed;
    private final String date_time;
    private final String station_name;
    private final String action_date_time;
    private final String modification_by_user;

    public WeatherDataReportDTO(Double precipitation, Double relativeHumidity, Double solarRadiation, Double temperature, Double windDirection, Double windSpeed, Timestamp dateTime, String stationName, Long actionDateTime, String modificationByUser) {
        this.precipitation = precipitation;
        this.relative_humidity = relativeHumidity;
        this.solar_radiation = solarRadiation;
        this.temperature = temperature;
        this.wind_direction = windDirection;
        this.wind_speed = windSpeed;
        this.date_time = String.valueOf(dateTime);
        this.station_name = stationName;
        this.action_date_time = UtilsMethods.longTimeStampToFormatString(String.valueOf(actionDateTime));
        this.modification_by_user = modificationByUser;
    }
}