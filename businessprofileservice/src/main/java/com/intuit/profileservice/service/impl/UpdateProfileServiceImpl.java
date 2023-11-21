package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.ProfileService;
import com.intuit.profileservice.service.UpdateProfileService;
import com.intuit.profileservice.transformer.UpdateProfileTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProfileServiceImpl implements UpdateProfileService {

    private final ProfileService profileService;
    private final UpdateProfileTransformer updateProfileTransformer;
    @Override
    public Profile updateProfile(UpdateProfileRequestDto request) {
        Optional<Profile> optProfiles = profileService.findByCustomeridAndLegalName(UUID.fromString(request.getCustomerId()),request.getLegalName());
        if (optProfiles.isEmpty()) {
                throw new ApplicationException("LCC", "Legal Name Cannot be changed");
        }
        return updateProfileTransformer.convertDtoToModel(request, optProfiles.get());
    }
}
