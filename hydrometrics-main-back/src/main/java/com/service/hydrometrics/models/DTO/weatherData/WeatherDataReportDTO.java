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
    private final String type;
    private final Double precipitation;
    private final Double relative_humidity;
    private final Double solar_radiation;
    private final Double temperature;
    private final Double wind_direction;
    private final Double wind_speed;
    private final String date_time;
    private final String station_name;
    private final String actionDate;
    private final String modification_by_user;

    public WeatherDataReportDTO(String type, Double precipitation, Double relativeHumidity, Double solarRadiation, Double temperature, Double windDirection, Double windSpeed, Timestamp dateTime, String stationName, Long actionDate, String modificationByUser) {
        this.type = type;
        this.precipitation = precipitation;
        this.relative_humidity = relativeHumidity;
        this.solar_radiation = solarRadiation;
        this.temperature = temperature;
        this.wind_direction = windDirection;
        this.wind_speed = windSpeed;
        this.date_time = String.valueOf(dateTime);
        this.station_name = stationName;
        this.actionDate = UtilsMethods.longTimeStampToFormatString(String.valueOf(actionDate));
        this.modification_by_user = modificationByUser;
    }
}