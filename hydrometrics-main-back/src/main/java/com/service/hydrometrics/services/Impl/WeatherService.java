package com.service.hydrometrics.services.Impl;

import com.service.hydrometrics.client.DataProcessor;
import com.service.hydrometrics.models.DB.entity.WeatherData;
import com.service.hydrometrics.models.DTO.weatherData.WeatherDataReportDTO;
import com.service.hydrometrics.models.enums.ActionLog;
import com.service.hydrometrics.models.enums.DataCamp;
import com.service.hydrometrics.repository.WeatherDataRepository;
import com.service.hydrometrics.services.IWeatherService;
import com.service.hydrometrics.utils.UtilsMethods;
import com.service.hydrometrics.utils.WeatherDataMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {

    private final EntityManager entityManager;
    private final WeatherDataRepository repo;
    private final DataProcessor dataProcessor;

    @Transactional(readOnly = true)
    @Override
    public Optional<WeatherData> getWeatherData(long id) {
        return repo.findById(id);
    }


    @Override
    @Transactional
    public void update(WeatherData weatherData) {
        repo.save(weatherData);
        UtilsMethods.generatePersistentLogger("WeatherData", ActionLog.UPDATE);
    }

    @Transactional
    @Override
    public Optional<List<WeatherData>> getInfoStation(long stationId, String dateStart, String dateEnd) {
        List<WeatherData> ListWeatherData = repo.findByStationId(dateStart, dateEnd, stationId);
        if (ListWeatherData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ListWeatherData);
    }

    @Override
    public Integer averageVariableRange(DataCamp predictionVariable, DataCamp correlationVariable, Integer predictionValue) {
        double upRange = predictionValue + ((predictionValue * 0.5) / 100);
        double downRange = predictionValue - ((predictionValue * 0.5) / 100);
        String queryStr = " SELECT AVG(w." + correlationVariable.getEnglish() + ") AS average FROM weather_data w LEFT JOIN alert a ON a.weather_data_id = w.id  WHERE w." + predictionVariable.getEnglish() + " BETWEEN " + downRange + " AND " + upRange + " AND a.weather_data_id IS NULL AND w." + correlationVariable.getEnglish() + " IS NOT NULL";
        Query query = entityManager.createNativeQuery(queryStr);
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    public List<WeatherDataReportDTO> getWeatherDataReport(String startDate, String endDate, long stationId) {
        long startTime = Timestamp.valueOf(startDate).getTime();
        long endTime = Timestamp.valueOf(endDate).getTime();
        String queryStr = "SELECT precipitation, relative_humidity, solar_radiation, temperature, wind_direction, wind_speed, date_time, s.name  AS station_name, ar.timestamp, u.username FROM weather_data_aud wda INNER JOIN station s ON wda.station_id = s.id INNER JOIN audit_revision ar ON wda.rev = ar.id INNER JOIN hydrometrics.user u ON ar.user_id = u.id WHERE station_id = :stationId AND ar.timestamp BETWEEN :startDate AND :endDate and wda.revtype = 1";
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter("stationId", stationId);
        query.setParameter("startDate", startTime);
        query.setParameter("endDate", endTime);
        List<Object[]> results = query.getResultList();

        List<WeatherDataReportDTO> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            Double precipitation = (Double) result[0];
            Double relativeHumidity = (Double) result[1];
            Double solarRadiation = (Double) result[2];
            Double temperature = (Double) result[3];
            Double windDirection = (Double) result[4];
            Double windSpeed = (Double) result[5];
            Timestamp dateTime = (Timestamp) result[6];
            String stationName = (String) result[7];
            Long dateAction = (Long) result[8];
            String modificationByUser = (String) result[9];
            WeatherDataReportDTO dto = new WeatherDataReportDTO(precipitation, relativeHumidity, solarRadiation, temperature, windDirection, windSpeed, dateTime, stationName, dateAction, modificationByUser);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void saveJsonWeatherDatas(List<WeatherDataMapper> weatherDataMappers) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        dataProcessor.sendWeatherDataMappers(weatherDataMappers, username);
    }
}
