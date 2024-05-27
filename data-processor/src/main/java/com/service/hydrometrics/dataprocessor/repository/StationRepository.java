package com.service.hydrometrics.dataprocessor.repository;

import com.service.hydrometrics.dataprocessor.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}