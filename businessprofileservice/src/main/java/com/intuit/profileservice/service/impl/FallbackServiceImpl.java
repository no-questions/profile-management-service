package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.models.ErrorCodes;
import com.intuit.profileservice.service.FallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FallbackServiceImpl implements FallbackService {

    private final ErrorCodesServiceImpl errorCodesServiceImpl;

    /**
     * Fallback method for retrieving error messages.
     *
     * @param errorCode The error code for which the error message is requested.
     * @return The fallback error message or an empty string if the error code is not found.
     */
    @Override
    public String fallbackForErrorMessage(String errorCode) {
        // Retrieve the error message from the error code service
        ErrorCodes errCode = errorCodesServiceImpl.findByErrorCode(errorCode);

        // Return the error message if found, otherwise return an empty string
        return (errCode != null) ? errCode.getErrormessage() : "";
    }
}

