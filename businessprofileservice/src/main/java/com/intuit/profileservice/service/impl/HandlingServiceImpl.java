package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.exceptions.NotFoundException;
import com.intuit.profileservice.service.HandlingService;
import org.springframework.stereotype.Service;

import static com.intuit.profileservice.util.Constants.*;

@Service
public class HandlingServiceImpl implements HandlingService {
    @Override
    public void handleDuplicateLegalName() {

        throw new ApplicationException(RESCODE_DC, RESCODE_DC_DEFAULT_MSG);
    }

    @Override
    public void handleValidationFailure() {
        throw new ApplicationException(RESCODE_VALIDATIONFAILURE,RESCODE_VALIDATIONFAILURE_DEFAULT_MSG);
    }

    @Override
    public void handleRateLimitExceeded() {
        throw new BadRequestException(RESCODE_RLE,RESCODE_RLE_DEFAULT_MSG);
    }

    @Override
    public void handleNoRecordFound() {
        throw new NotFoundException(RESCODE_NF,RESCODE_NF_DEFAULT_MSG);
    }

    @Override
    public void handleDuplicateLegalName(String message) {

        throw new ApplicationException(RESCODE_DC, message);
    }

    @Override
    public void handleValidationFailure(String message) {
        throw new ApplicationException(RESCODE_VALIDATIONFAILURE,message);
    }

    @Override
    public void handleBadRequest(String message) {
        throw new BadRequestException(RESCODE_RLE,message);
    }

    @Override
    public void handleNoRecordFound(String message) {
        throw new NotFoundException(RESCODE_NF,message);
    }


}
