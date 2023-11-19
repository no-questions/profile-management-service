package com.intuit.profileservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.profileservice.controller.EncryptDecryptController;
import com.intuit.profileservice.models.AuditLog;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.AuditLogRepository;
import com.intuit.profileservice.util.EncryptDecryptUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;
    private final EncryptDecryptUtil encryptDecryptUtil;

    @Async
    public void saveToAuditLog(Profile profile) {
        logMethodEntry("saveToAuditLog");

        AuditLog auditLog = new AuditLog();
        if (profile.getIsmodified())
            auditLog.setAction("UPDATE");
        else
            auditLog.setAction("CREATE");
        auditLog.setIdentifier(profile.getId().toString());
        auditLog.setCurrentprofile(encryptDecryptUtil.encrypt(convertProfileToJson(profile)));
        auditLogRepository.save(auditLog);

        logMethodExit("saveToAuditLog");
    }

    public String convertProfileToJson(Profile profile) {
        logMethodEntry("convertProfileToJson");

        try {
            String json = objectMapper.writeValueAsString(profile);
            logMethodExit("convertProfileToJson");
            return json;
        } catch (JsonProcessingException e) {
            logException("convertProfileToJson", e);
            return "{}";
        }
    }

    private void logMethodEntry(String methodName) {
        logger.debug("Entering {} method", methodName);
    }

    private void logMethodExit(String methodName) {
        logger.debug("Exiting {} method", methodName);
    }

    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}

