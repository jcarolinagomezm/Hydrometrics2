package com.service.hydrometrics.services;

import com.service.hydrometrics.models.enums.DataCamp;
import com.service.hydrometrics.utils.WeatherDataMapper;
import com.service.hydrometrics.models.DB.entity.WeatherData;
import com.service.hydrometrics.models.DTO.weatherData.WeatherDataReportDTO;

import java.util.List;
import java.util.Optional;

public interface IWeatherService {
    void update(WeatherData weatherData);
    Optional<WeatherData> getWeatherData(long id);
    Optional<List<WeatherData>> getInfoStation(long stationId, String dateStart, String dateEnd);
    Integer averageVariableRange(DataCamp predictionVariable, DataCamp correlationVariable, Integer prediction);
    List<WeatherDataReportDTO> getWeatherDataReport(String startDate, String endDate, long stationId);
    void saveJsonWeatherDatas(List<WeatherDataMapper> weatherDataMappers);
}
