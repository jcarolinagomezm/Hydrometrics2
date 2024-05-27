package com.service.hydrometrics.services.Impl;


import com.service.hydrometrics.utils.UtilsMethods;
import com.service.hydrometrics.models.DB.entity.Prediction;
import com.service.hydrometrics.models.enums.ActionLog;
import com.service.hydrometrics.repository.PredictionRepository;
import com.service.hydrometrics.services.IPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PredictionService implements IPredictionService {

    private final PredictionRepository repo;

    @Transactional
    @Override
    public Prediction save(Prediction prediction) {
        var predictionSaved = repo.save(prediction);
        UtilsMethods.generatePersistentLogger("Prediction", ActionLog.CREATE);
        return predictionSaved;
    }
}
