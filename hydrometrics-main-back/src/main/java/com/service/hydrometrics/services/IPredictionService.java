package com.service.hydrometrics.services;

import com.service.hydrometrics.models.DB.entity.Prediction;

public interface IPredictionService {
    Prediction save(Prediction prediction);
}
