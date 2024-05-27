package com.service.hydrometrics.repository;

import com.service.hydrometrics.models.DB.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Query(value = "SELECT wd.* FROM weather_data wd WHERE wd.station_id = :stationId AND wd.date_time BETWEEN :start AND :end", nativeQuery = true)
    List<WeatherData> findByStationId(@Param("start") String start, @Param("end")String end, @Param("stationId") long stationId);
}