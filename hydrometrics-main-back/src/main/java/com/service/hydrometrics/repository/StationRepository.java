package com.service.hydrometrics.repository;

import com.service.hydrometrics.models.DB.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByName(String name);

    @Query("select s from Station s")
    List<Station> getAll();
}