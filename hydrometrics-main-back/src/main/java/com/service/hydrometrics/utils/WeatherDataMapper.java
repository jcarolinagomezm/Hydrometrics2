package com.service.hydrometrics.utils;

public record WeatherDataMapper(String date_time,
                                Double precipitation,
                                Double relative_humidity,
                                Double solar_radiation,
                                Double temperature,
                                Double wind_direction,
                                Double wind_speed,
                                long station_id) {
}
