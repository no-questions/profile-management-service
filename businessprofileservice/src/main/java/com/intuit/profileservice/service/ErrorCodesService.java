package com.intuit.profileservice.service;

import java.util.List;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.models.ErrorCodes;

public interface ErrorCodesService {

    List<ErrorCodes> getAll();

    ErrorCodes findByErrorCode(String errorCode);

    Boolean checkForFailure(ProfileValidationsResp resp);

    Boolean fallbackForFailureIdentification(ProfileValidationsResp resp);

    List<ErrorCodeDto> convertList(List<ErrorCodes> sourceList);
}

