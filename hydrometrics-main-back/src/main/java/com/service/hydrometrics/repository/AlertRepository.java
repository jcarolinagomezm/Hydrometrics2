package com.service.hydrometrics.repository;

import com.service.hydrometrics.models.DB.entity.Alert;
import com.service.hydrometrics.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByStatusIs(Status status);
}