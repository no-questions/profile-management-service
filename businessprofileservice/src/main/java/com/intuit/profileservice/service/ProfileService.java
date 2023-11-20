package com.intuit.profileservice.service;

import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProfileRepository profileRepository;
    private final AuditLogService auditLogService;

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

    public Profile saveProfile(Profile profile) {
        logMethodEntry("saveProfile");
        try {
            Profile newProfile = profileRepository.save(profile);
            auditLogService.saveToAuditLog(newProfile);
            logMethodExit("saveProfile");
            return newProfile;
        } catch (Exception e) {
            logException("saveProfile", e);
            throw e; // Rethrow the exception after logging
        }
    }

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

    public Optional<Profile> findByCustomeridAndLegalName(UUID id, String legalName) {
        return profileRepository.findByIdAndLegalname(id,legalName);
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

