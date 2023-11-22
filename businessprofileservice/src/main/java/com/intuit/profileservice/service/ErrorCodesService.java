package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.ErrorCodeDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.models.ErrorCodes;

import java.util.List;

public interface ErrorCodesService {

    List<ErrorCodes> getAll();

    ErrorCodes findByErrorCode(String errorCode);

    Boolean checkForFailure(ProfileValidationsResp resp);

    List<ErrorCodeDto> convertList(List<ErrorCodes> sourceList);
}

