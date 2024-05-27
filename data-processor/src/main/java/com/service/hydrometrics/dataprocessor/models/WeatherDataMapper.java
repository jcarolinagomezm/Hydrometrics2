package com.service.hydrometrics.dataprocessor.models;

public record WeatherDataMapper(Double precipitation,
                                Double relative_humidity,
                                Double solar_radiation,
                                Double temperature,
                                Double wind_direction,
                                Double wind_speed,
                                String date_time, long station_id) {
}
