package com.service.hydrometrics.dataprocessor.utils;

import com.service.hydrometrics.dataprocessor.enums.ActionLog;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;

@Slf4j
public class UtilsMethods {

    public static void generatePersistentLogger(String entity, ActionLog actionLog, String username) {
        log.info(" {} --> User: {} , Fecha-Hora: {} , Accion: {}", entity, username, Date.from(Instant.now()), actionLog);
    }

}
