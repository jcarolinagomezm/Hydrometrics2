package com.service.hydrometrics.controller;

import com.service.hydrometrics.models.DB.entity.Station;
import com.service.hydrometrics.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {

    private final StationRepository repo;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_AUDITOR','ROLE_USER')")
    @GetMapping("/")
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(repo.findAll());
    }
}
