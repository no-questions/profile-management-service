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

import static com.intuit.profileservice.util.Constants.RESCODE_LCC;
import static com.intuit.profileservice.util.Constants.RESCODE_LCC_DEFAULT_MSG;

@Service
@RequiredArgsConstructor
public class UpdateProfileServiceImpl implements UpdateProfileService {

    private final ProfileService profileService;
    private final UpdateProfileTransformer updateProfileTransformer;

    /**
     * Updates a profile based on the provided request.
     *
     * @param request The update profile request.
     * @return The updated profile.
     * @throws ApplicationException if the legal name is changed.
     */
    @Override
    public Profile updateProfile(UpdateProfileRequestDto request) {
        // Retrieve the profile based on customer ID and legal name
        Optional<Profile> optProfiles = profileService.findByCustomeridAndLegalName(
                UUID.fromString(request.getCustomerId()), request.getLegalName());

        // If no profile is found, throw an application-level exception
        if (optProfiles.isEmpty()) {
            throw new ApplicationException(RESCODE_LCC, RESCODE_LCC_DEFAULT_MSG);
        }

        // Convert the DTO to a model and update the profile
        return updateProfileTransformer.convertDtoToModel(request, optProfiles.get());
    }
}
