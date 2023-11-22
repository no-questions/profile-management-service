package com.intuit.profileservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.profileservice.models.AuditLog;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.AuditLogRepository;
import com.intuit.profileservice.service.AuditLogService;
import com.intuit.profileservice.util.EncryptDecryptUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;
    private final EncryptDecryptUtil encryptDecryptUtil;

    /**
     * Asynchronously saves a profile update or creation to the audit log.
     *
     * @param profile The Profile object to be saved to the audit log.
     */
    @Async
    @Override
    public void saveToAuditLog(Profile profile) {
        logMethodEntry("saveToAuditLog");

        AuditLog auditLog = new AuditLog();
        // Determine the action based on whether the profile is modified
        if (profile.getIsmodified()) {
            auditLog.setAction("UPDATE");
        } else {
            auditLog.setAction("CREATE");
        }
        auditLog.setIdentifier(profile.getId().toString());
        // Encrypt and save the current profile state to the audit log
        auditLog.setCurrentprofile(encryptDecryptUtil.encrypt(convertProfileToJson(profile)));
        auditLogRepository.save(auditLog);

        logMethodExit("saveToAuditLog");
    }

    /**
     * Converts a Profile object to its JSON representation.
     *
     * @param profile The Profile object to be converted to JSON.
     * @return The JSON representation of the Profile, or an empty JSON object on conversion failure.
     */
    @Override
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

    // Helper method to log method entry
    private void logMethodEntry(String methodName) {
        logger.debug("Entering {} method", methodName);
    }

    // Helper method to log method exit
    private void logMethodExit(String methodName) {
        logger.debug("Exiting {} method", methodName);
    }

    // Helper method to log exceptions
    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}
