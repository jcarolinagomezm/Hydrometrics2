package com.service.hydrometrics.utils;

import com.service.hydrometrics.models.enums.ActionLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class UtilsMethods {

    public static HashMap<String, String> validatorBody(BindingResult bindingResult) {
        HashMap<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return errors;
    }

    public static String generateJwtKey() {
        try {
            // Generar una clave aleatoria de 256 bits (32 bytes)
            SecureRandom secureRandom = new SecureRandom();
            byte[] key = new byte[32];
            secureRandom.nextBytes(key);

            // Codificar la clave en base64
            return Base64.getEncoder().encodeToString(key);
        } catch (Exception e) {
            System.err.println("Error generando la clave JWT: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return "application";
    }

    public static void generatePersistentLogger(String entity, ActionLog actionLog) {
        log.info(" {} --> User: {} , Fecha-Hora: {} , Accion: {}", entity, UtilsMethods.getCurrentUserName(), Date.from(Instant.now()), actionLog);
    }

    public static void generateAuthLogger(String usuario, HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        log.info(" Usuario {} inicio seción a las {} desde --> {}", usuario, Instant.now(), ipAddress);
    }

    public static String longTimeStampToFormatString(String stringtimestamp) {
        if (stringtimestamp.startsWith("N") || stringtimestamp.equals("0")) {
            return "No Modification Date";
        }
        long timestamp = Long.parseLong(stringtimestamp);
        Date date = new Date(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}
