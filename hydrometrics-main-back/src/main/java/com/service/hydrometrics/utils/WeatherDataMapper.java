package com.service.hydrometrics.utils;

public record WeatherDataMapper(String date_time,
                                Integer precipitation,
                                Integer relative_humidity,
                                Integer solar_radiation,
                                Integer temperature,
                                Integer wind_direction,
                                Integer wind_speed,
                                long station_id) {
}
