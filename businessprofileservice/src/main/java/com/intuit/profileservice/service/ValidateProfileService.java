package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileValidationsResp;

public interface ValidateProfileService {


    boolean preRequestCreationValidations(ProfileRequestDto request);

    ProfileValidationsResp validateProfile(ProfileRequestDto request);


}
