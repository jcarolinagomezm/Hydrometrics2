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
    private final Integer precipitation;
    private final Integer relative_humidity;
    private final Integer solar_radiation;
    private final Integer temperature;
    private final Integer wind_direction;
    private final Integer wind_speed;
    private final String date_time;
    private final String station_name;
    private final String action_date_time;
    private final String modification_by_user;

    public WeatherDataReportDTO(Number precipitation, Number relativeHumidity, Number solarRadiation, Number temperature, Number windDirection, Number windSpeed, Timestamp dateTime, String stationName, Long actionDateTime, String modificationByUser) {
        this.precipitation = (precipitation != null) ? precipitation.intValue() : null;
        this.relative_humidity = (relativeHumidity != null) ? relativeHumidity.intValue() : null;
        this.solar_radiation = (solarRadiation != null) ? solarRadiation.intValue() : null;
        this.temperature = (temperature != null) ? temperature.intValue() : null;
        this.wind_direction = (windDirection != null) ? windDirection.intValue() : null;
        this.wind_speed = (windSpeed != null) ? windSpeed.intValue() : null;
        this.date_time = String.valueOf(dateTime);
        this.station_name = stationName;
        this.action_date_time = UtilsMethods.longTimeStampToFormatString(String.valueOf(actionDateTime));
        this.modification_by_user = modificationByUser;
    }
}