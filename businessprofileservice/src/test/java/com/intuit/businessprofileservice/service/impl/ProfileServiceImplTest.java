package com.intuit.businessprofileservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.repository.ProfileRepository;
import com.intuit.profileservice.service.AuditLogService;
import com.intuit.profileservice.service.impl.ProfileServiceImpl;

import java.util.*;

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
        // Arrange
        List<Profile> mockProfiles = Arrays.asList(new Profile(), new Profile());
        when(profileRepository.findAll()).thenReturn(mockProfiles);

        // Act
        List<Profile> result = profileService.getAllProfiles();

        // Assert
        assertEquals(mockProfiles, result);
    }

    @Test
    void testGetProfileById_Success() {
        // Arrange
        UUID profileId = UUID.randomUUID();
        Profile mockProfile = new Profile();
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(mockProfile));

        // Act
        Optional<Profile> result = profileService.getProfileById(profileId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testSaveProfile_Success() {
        // Arrange
        Profile inputProfile = new Profile();
        when(profileRepository.save(inputProfile)).thenReturn(inputProfile);

        // Act
        Profile result = profileService.saveProfile(inputProfile);

        // Assert
        assertEquals(inputProfile, result);
        verify(auditLogService, times(1)).saveToAuditLog(inputProfile);
    }

    @Test
    void testDeleteProfile_Success() {
        // Arrange
        UUID profileId = UUID.randomUUID();

        // Act
        assertDoesNotThrow(() -> profileService.deleteProfile(profileId));

        // Assert
        verify(profileRepository, times(1)).deleteById(profileId);
    }

    @Test
    void testGetProfileByCompanyName_Success() {
        // Arrange
        String companyName = "ExampleCompany";
        Profile mockProfile = new Profile();
        when(profileRepository.findByLegalname(companyName)).thenReturn(Optional.of(mockProfile));

        // Act
        Optional<Profile> result = profileService.getProfileByCompanyName(companyName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testFindByCustomeridAndLegalName_Success() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        String legalName = "ExampleLegalName";
        Profile mockProfile = new Profile();
        when(profileRepository.findByIdAndLegalname(customerId, legalName)).thenReturn(Optional.of(mockProfile));

        // Act
        Optional<Profile> result = profileService.findByCustomeridAndLegalName(customerId, legalName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
    }

    @Test
    void testGetAllProfiles_Exception() {
        // Arrange
        when(profileRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profileService.getAllProfiles());
    }
}
