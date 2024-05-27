package com.service.hydrometrics.dataprocessor.entity;

import com.service.hydrometrics.dataprocessor.enums.DataCamp;
import com.service.hydrometrics.dataprocessor.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DataCamp dataCamp;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    private WeatherData weatherData;

    public Alert(DataCamp dataCamp, Status status, WeatherData weatherData) {
        this.dataCamp = dataCamp;
        this.status = status;
        this.weatherData = weatherData;
    }
}
