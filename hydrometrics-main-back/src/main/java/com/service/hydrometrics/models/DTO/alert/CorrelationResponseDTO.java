package com.service.hydrometrics.models.DTO.alert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CorrelationResponseDTO implements Serializable {
    private final String weatherDataVariable;
    private final Double promedioVariable;
}
