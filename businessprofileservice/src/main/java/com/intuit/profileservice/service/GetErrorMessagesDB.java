package com.intuit.profileservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.intuit.profileservice.repository.ErrorCodesRepository;


public class GetErrorMessagesDB implements GetErrorMessages {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ErrorCodesRepository errorCodesRepository;

    @Override
    public String fetchErrorMessage(String errorCode) {
        logMethodEntry("fetchErrorMessage");

        try {
            String errorMessage = errorCodesRepository.findByErrorcode(errorCode).getErrormessage();
            logMethodExit("fetchErrorMessage");
            return errorMessage;
        } catch (Exception e) {
            logException("fetchErrorMessage", e);
            throw e; // Rethrow the exception after logging
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
