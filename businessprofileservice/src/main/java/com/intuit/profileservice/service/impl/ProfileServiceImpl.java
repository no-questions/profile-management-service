package com.intuit.profileservice.service.impl;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.ProfileRepository;
import com.intuit.profileservice.service.AuditLogService;
import com.intuit.profileservice.service.ProfileService;
import com.intuit.profileservice.transformer.UpdateProfileTransformer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    // Autowired dependencies using Lombok's @RequiredArgsConstructor
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileRepository profileRepository;
    private final AuditLogService auditLogService;
    private final UpdateProfileTransformer updateProfileTransformer;

    /**
     * Retrieves all profiles.
     *
     * @return List of all profiles.
     * @throws Exception if an error occurs during the retrieval process.
     */
    @Override
    public List<Profile> getAllProfiles() {
        logMethodEntry("getAllProfiles");
        try {
            List<Profile> profiles = profileRepository.findAll();
            logMethodExit("getAllProfiles");
            return profiles;
        } catch (Exception e) {
            logException("getAllProfiles", e);
            throw e; // Rethrow the exception after logging
        }
    }

    /**
     * Retrieves a profile by its ID.
     *
     * @param id The ID of the profile.
     * @return Optional of the retrieved profile.
     * @throws Exception if an error occurs during the retrieval process.
     */
    @Override
    public Optional<Profile> getProfileById(UUID id) {
        logMethodEntry("getProfileById");
        try {
            Optional<Profile> profile = profileRepository.findById(id);
            logMethodExit("getProfileById");
            return profile;
        } catch (Exception e) {
            logException("getProfileById", e);
            throw e; // Rethrow the exception after logging
        }
    }

    /**
     * Saves a new profile and logs the operation in the audit log.
     *
     * @param profile The profile to be saved.
     * @return The saved profile.
     * @throws Exception if an error occurs during the save process.
     */
    @Override
    public Profile saveProfile(Profile profile) {
        logMethodEntry("saveProfile");
        try {
            Profile newProfile = profileRepository.save(profile);
            auditLogService.saveToAuditLog(newProfile);
            logMethodExit("saveProfile");
            return newProfile;
        } catch (Exception e) {
            logException("saveProfile", e);
            throw e;
        }
    }

    /**
     * Deletes a profile by its ID.
     *
     * @param id The ID of the profile to be deleted.
     * @throws Exception if an error occurs during the delete process.
     */
    @Override
    public void deleteProfile(UUID id) {
        logMethodEntry("deleteProfile");
        try {
            profileRepository.deleteById(id);
            logMethodExit("deleteProfile");
        } catch (Exception e) {
            logException("deleteProfile", e);
            throw e; // Rethrow the exception after logging
        }
    }

    /**
     * Retrieves a profile by its company name (legal name).
     *
     * @param companyName The legal name of the company.
     * @return Optional of the retrieved profile.
     * @throws Exception if an error occurs during the retrieval process.
     */
    @Override
    public Optional<Profile> getProfileByCompanyName(String companyName) {
        logMethodEntry("getProfileByCompanyName");
        try {
            Optional<Profile> profile = profileRepository.findByLegalname(companyName);
            logMethodExit("getProfileByCompanyName");
            return profile;
        } catch (Exception e) {
            logException("getProfileByCompanyName", e);
            throw e; // Rethrow the exception after logging
        }
    }

    /**
     * Retrieves a profile by its customer ID and legal name.
     *
     * @param id        The customer ID.
     * @param legalName The legal name of the company.
     * @return Optional of the retrieved profile.
     */
    @Override
    public Optional<Profile> findByCustomeridAndLegalName(UUID id, String legalName) {
        return profileRepository.findByIdAndLegalname(id, legalName);
    }

    /**
     * Converts a profile to an update profile request DTO.
     *
     * @param profile The profile to be converted.
     * @return The update profile request DTO.
     */
    @Override
    public UpdateProfileRequestDto convertProfileToUpdateRequest(Profile profile) {
        return updateProfileTransformer.convertModelToDto(profile);
    }

    private void logMethodEntry(String methodName) {
        logger.debug("Entering {} method", methodName);
    }

    private void logMethodExit(String methodName) {
        logger.debug("Exiting {} method", methodName);
    }

    private void logException(String methodName, Exception e) {
        logger.error("{}: Error occurred during execution: {}", methodName, e.getMessage());
    }
}

