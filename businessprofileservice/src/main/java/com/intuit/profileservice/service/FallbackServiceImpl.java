package com.intuit.profileservice.service;

import org.springframework.stereotype.Component;

import com.intuit.profileservice.models.ErrorCodes;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FallbackServiceImpl implements FallbackService {

    private final ErrorCodesServiceImpl errorCodesServiceImpl;

    @Override
    public String fallbackForErrorMessage(String errorCode) {
        ErrorCodes errCode = errorCodesServiceImpl.findByErrorCode(errorCode);
        return errorCode!=null?errCode.getErrormessage():"";
    }
    
}
