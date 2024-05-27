package com.service.hydrometrics.services;

import com.service.hydrometrics.models.DB.entity.Alert;
import com.service.hydrometrics.models.DTO.alert.AlertResponseDTO;
import com.service.hydrometrics.models.DTO.alert.ConfirmAlertDTO;
import com.service.hydrometrics.models.DTO.alert.CorrelationRequestDTO;
import com.service.hydrometrics.models.DTO.alert.CorrelationResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IAlertService {
    Optional<Alert> getAlert(Long alertId);
    List<AlertResponseDTO> getAlertsToReview();
    boolean processConfirmAlert(ConfirmAlertDTO confirm);
    List<CorrelationResponseDTO> getCorrelationAlert(CorrelationRequestDTO correlationRequest);
}
