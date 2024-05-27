package com.service.hydrometrics.dataprocessor.service;

import com.service.hydrometrics.dataprocessor.entity.Alert;
import com.service.hydrometrics.dataprocessor.entity.Station;
import com.service.hydrometrics.dataprocessor.entity.WeatherData;
import com.service.hydrometrics.dataprocessor.enums.DataCamp;
import com.service.hydrometrics.dataprocessor.enums.Status;
import com.service.hydrometrics.dataprocessor.models.WeatherDataMapper;
import com.service.hydrometrics.dataprocessor.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataProcess {

    private final StationRepository stationRepo;

    public List<WeatherData> WeatherDataMapperToEntity(List<WeatherDataMapper> weatherDataMappers) {
        log.info("Procesando los Mappers a Entidades ....");
        return weatherDataMappers.stream().map(weatherDataMapper -> {
            WeatherData weatherData = new WeatherData();
            Optional<Station> oStation = stationRepo.findById(weatherDataMapper.station_id());
            if (oStation.isPresent()) {
                Station station = oStation.get();
                String formattedDateTime = weatherDataMapper.date_time().replace('T', ' ');
                weatherData.setDateTime(Timestamp.valueOf(formattedDateTime));
                weatherData.setStation(station);
                weatherData.setPrecipitation(weatherDataMapper.precipitation());
                weatherData.setTemperature(weatherDataMapper.temperature());
                weatherData.setRelativeHumidity(weatherDataMapper.relative_humidity());
                weatherData.setWindDirection(weatherDataMapper.wind_direction());
                weatherData.setWindSpeed(weatherDataMapper.wind_speed());
                weatherData.setSolarRadiation(weatherDataMapper.solar_radiation());
            }
            return weatherData;
        }).toList();
    }

    public List<Alert> analizedData(WeatherData weatherData) {
        String[] variables = {"temperatura", "direccion viento", "humedad relativa", "radiacion solar", "precipitacion", "velocidad viento"};
        Double[] upperLimits = {26.0, 360.0, 100.0, 1400.0, 60.0, 11.8};
        Double[] values = {weatherData.getTemperature(), weatherData.getWindDirection(), weatherData.getRelativeHumidity(), weatherData.getSolarRadiation(), weatherData.getPrecipitation(), weatherData.getWindSpeed()};
        return IntStream.range(0, variables.length)
                .filter(i -> values[i] == null || values[i] < 0 || values[i] > upperLimits[i])
                .mapToObj(i -> DataCamp.valueOf(variables[i].toUpperCase().replace(" ", "_")))
                .map(camp -> new Alert(camp, Status.UNCHECKED, weatherData))
                .toList();
    }
}
