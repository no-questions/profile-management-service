package com.intuit.profileservice.service;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.models.Profile;

public interface UpdateProfileService {
    Profile updateProfile(UpdateProfileRequestDto request);
}
