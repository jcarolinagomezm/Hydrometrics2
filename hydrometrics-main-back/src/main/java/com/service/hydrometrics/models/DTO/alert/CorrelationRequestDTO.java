package com.service.hydrometrics.models.DTO.alert;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CorrelationRequestDTO implements Serializable {
    @NotNull
    private final Integer predictionValue;
    @NotNull
    private final long stationId;
    @NotEmpty
    private final String dataCamp;
}