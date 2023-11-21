package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;
import com.intuit.profileservice.dto.UpdateProfileRequestDto;

public interface ValidateProfileService {



    boolean preRequestCreationValidations(ProfileRequestDto request);

    ProfileValidationsResp validateProfile(ProfileRequestDto request);


}
