package com.intuit.profileservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.models.Profile;

public interface ProfileService {

    List<Profile> getAllProfiles();

    Optional<Profile> getProfileById(UUID id);

    Profile saveProfile(Profile profile);

    void deleteProfile(UUID id);

    Optional<Profile> getProfileByCompanyName(String companyName);

    Optional<Profile> findByCustomeridAndLegalName(UUID id, String legalName);

    UpdateProfileRequestDto convertProfileToUpdateRequest(Profile profile);
}
