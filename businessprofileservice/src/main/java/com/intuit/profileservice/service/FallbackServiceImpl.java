package com.intuit.profileservice.service;

import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
public class FallbackServiceImpl implements FallbackService {

    @NonNull
    private final GetErrorMessagesDB getMessageFromDB = new GetErrorMessagesDB();

    @Override
    public String fallbackForErrorMessage(String errorCode) {
        return getMessageFromDB.fetchErrorMessage(errorCode);
    }
    
}
