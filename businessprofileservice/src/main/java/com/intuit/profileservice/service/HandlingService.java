package com.intuit.profileservice.service;

public interface HandlingService {
    void handleDuplicateLegalName();
    void handleValidationFailure();

    void handleRateLimitExceeded();

    void handleNoRecordFound();
}
