package com.service.hydrometrics.dataprocessor.utils;

import com.service.hydrometrics.dataprocessor.enums.ActionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class UtilsMethods {

    public static void generatePersistentLogger(String entity, ActionLog actionLog, String username) {
        log.info(" {} --> User: {} , Fecha-Hora: {} , Accion: {}", entity, username, Date.from(Instant.now()), actionLog);
    }

}
