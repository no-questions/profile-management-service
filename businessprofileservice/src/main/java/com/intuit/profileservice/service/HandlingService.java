package com.intuit.profileservice.service;

public interface HandlingService {
    void handleDuplicateLegalName();

    void handleValidationFailure();

    void handleRateLimitExceeded();

    void handleNoRecordFound();

    void handleDuplicateLegalName(String message);

    void handleValidationFailure(String message);

    void handleBadRequest(String message);

    void handleNoRecordFound(String message);
}
