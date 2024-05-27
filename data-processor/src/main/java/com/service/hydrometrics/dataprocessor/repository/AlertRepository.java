package com.service.hydrometrics.dataprocessor.repository;

import com.service.hydrometrics.dataprocessor.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}