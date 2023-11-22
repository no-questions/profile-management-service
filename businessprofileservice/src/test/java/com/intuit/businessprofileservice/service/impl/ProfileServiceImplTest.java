package com.intuit.businessprofileservice.service.impl;

import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.ProfileRepository;
import com.intuit.profileservice.service.AuditLogService;
import com.intuit.profileservice.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProfiles_Success() {
        List<Profile> mockProfiles = Arrays.asList(new Profile(), new Profile());
        when(profileRepository.findAll()).thenReturn(mockProfiles);
        List<Profile> result = profileService.getAllProfiles();
        assertEquals(mockProfiles, result);
    }

    @Test
    void testGetProfileById_Success() {
        UUID profileId = UUID.randomUUID();
        Profile mockProfile = new Profile();
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(mockProfile));
        Optional<Profile> result = profileService.getProfileById(profileId);
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testSaveProfile_Success() {
        Profile inputProfile = new Profile();
        when(profileRepository.save(inputProfile)).thenReturn(inputProfile);
        Profile result = profileService.saveProfile(inputProfile);
        assertEquals(inputProfile, result);
        verify(auditLogService, times(1)).saveToAuditLog(inputProfile);
    }

    @Test
    void testDeleteProfile_Success() {
        UUID profileId = UUID.randomUUID();
        assertDoesNotThrow(() -> profileService.deleteProfile(profileId));
        verify(profileRepository, times(1)).deleteById(profileId);
    }

    @Test
    void testGetProfileByCompanyName_Success() {
        String companyName = "ExampleCompany";
        Profile mockProfile = new Profile();
        when(profileRepository.findByLegalname(companyName)).thenReturn(Optional.of(mockProfile));
        Optional<Profile> result = profileService.getProfileByCompanyName(companyName);
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testFindByCustomeridAndLegalName_Success() {
        UUID customerId = UUID.randomUUID();
        String legalName = "ExampleLegalName";
        Profile mockProfile = new Profile();
        when(profileRepository.findByIdAndLegalname(customerId, legalName)).thenReturn(Optional.of(mockProfile));
        Optional<Profile> result = profileService.findByCustomeridAndLegalName(customerId, legalName);
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testGetAllProfiles_Exception() {
        when(profileRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));
        assertThrows(RuntimeException.class, () -> profileService.getAllProfiles());
    }
}
