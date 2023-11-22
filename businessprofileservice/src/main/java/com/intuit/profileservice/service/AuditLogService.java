package com.intuit.profileservice.service;

import com.intuit.profileservice.models.Profile;

public interface AuditLogService {

    void saveToAuditLog(Profile profile);

    String convertProfileToJson(Profile profile);
}

