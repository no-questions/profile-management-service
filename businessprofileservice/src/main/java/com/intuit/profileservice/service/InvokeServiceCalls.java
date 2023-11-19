package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;

public interface InvokeServiceCalls {
    BaseResponse validateCreation(ProfileRequestDto req);
}
