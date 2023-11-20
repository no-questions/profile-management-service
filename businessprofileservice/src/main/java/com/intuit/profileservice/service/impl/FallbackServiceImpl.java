package com.intuit.profileservice.service.impl;

import org.springframework.stereotype.Component;

import com.intuit.profileservice.models.ErrorCodes;
import com.intuit.profileservice.service.FallbackService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FallbackServiceImpl implements FallbackService {

    private final ErrorCodesServiceImpl errorCodesServiceImpl;

    @Override
    public String fallbackForErrorMessage(String errorCode) {
        ErrorCodes errCode = errorCodesServiceImpl.findByErrorCode(errorCode);
        return errCode!=null?errCode.getErrormessage():"";
    }
    
}
