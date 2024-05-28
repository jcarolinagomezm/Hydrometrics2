package com.service.hydrometrics.models.DTO.alert;

import java.io.Serializable;

public record ConfirmAlertDTO(long alertId, String dataCamp, Integer prediction) implements Serializable {
}
