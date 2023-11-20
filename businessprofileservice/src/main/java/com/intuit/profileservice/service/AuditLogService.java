package com.intuit.profileservice.service;

import org.springframework.scheduling.annotation.Async;

import com.intuit.profileservice.models.Profile;

public interface AuditLogService {

    @Async
    void saveToAuditLog(Profile profile);

    String convertProfileToJson(Profile profile);
}

