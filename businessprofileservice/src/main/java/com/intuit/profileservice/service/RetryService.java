package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.BaseResponse;

public interface RetryService {

    Boolean retryCreationValidation(BaseResponse response);

    Boolean retryUpdateValidation(BaseResponse response);
}
