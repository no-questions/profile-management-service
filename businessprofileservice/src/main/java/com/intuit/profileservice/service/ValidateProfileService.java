package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;

public interface ValidateProfileService {


    BaseResponse preRequestValidations(ProfileRequestDto request);

    ProfileValidationsResp validateProfile(ProfileRequestDto request);


}
