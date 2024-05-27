package com.service.hydrometrics.repository;

import com.service.hydrometrics.models.DB.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    @Query("select p from Prediction p where p.alert.id = :alertId")
    Double getValueByAlertId(@Param("alertId") long alertId);
}