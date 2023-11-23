package com.intuit.profileservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.profileservice.dto.CreateProfileResponseDto;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.impl.ErrorCodesServiceImpl;
import com.intuit.profileservice.service.impl.HandlingServiceImpl;
import com.intuit.profileservice.service.impl.ProfileServiceImpl;
import com.intuit.profileservice.service.impl.ValidateProfileServiceImpl;
import com.intuit.profileservice.transformer.CreateProfileTransformer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.UUID;

//@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
////    @InjectMocks
    @Mock
    private ProfileServiceImpl profileService;
    @Mock
    private ValidateProfileServiceImpl validateProfileService;
    @Mock
    private CreateProfileTransformer createProfileTransformer;
    @Mock
    private ErrorCodesServiceImpl errorCodesService;
    @Mock
    private HandlingServiceImpl handlingService;
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
//
    private final ObjectMapper objectMapper = new ObjectMapper();



    @InjectMocks
    private CreateController createController;
//    = new CreateController(profileService,validateProfileService,createProfileTransformer,errorCodesService,handlingService);
    // Mock services, transformers, and repositories as needed and inject them into the controller

    @Test
    void testCreateProfile_ValidRequest_ReturnsAccepted() throws JsonProcessingException {
        ProfileRequestDto requestDto = createValidProfileRequest();
        Mockito.when(validateProfileService.preRequestCreationValidations(requestDto)).thenReturn(true);
        Mockito.when(errorCodesService.checkForFailure(validateProfileService.validateProfile(requestDto))).thenReturn(false);
        when(createProfileTransformer.convertDtoToModel(requestDto)).thenReturn(createMockProfile());
//        doNothing().when(profileService).saveProfile(any());

//        verify(profileService, times(1)).saveProfile(any());
        Profile profile = new Profile();
        profile.setId(UUID.fromString("9e1d5153-4db9-4f97-a5ee-acf917617486"));
        profile.setCompanyname(requestDto.getCompanyName());
        profile.setLegalname(requestDto.getLegalName());
        profile.setEmail(requestDto.getEmail());
        profile.setWebsite(requestDto.getWebsite());

//        profile.setBusinessaddress(createProfileTransformer.con(requestDto.getBusinessAddress()));
//        profile.setLegaladdress(createProfileTransformer.convertDtoToModel(requestDto.getLegalAddress()));
//        profile.setPandetails(createProfileTransformer.convertDtoToModel(requestDto.getTaxidentifiers()));
//        profile.setEindetails(createProfileTransformer.convertDtoToModel(requestDto.getTaxidentifiers()));
        Mockito.when(profileService.saveProfile(profile)).thenReturn(profile);
        ResponseEntity<CreateProfileResponseDto> response = restTemplate.postForEntity("http://localhost:"+port+"/create/profile",requestDto,CreateProfileResponseDto.class,new HashMap<String,String>());
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

//            mockMvc.perform(post("/create/profile").accept(MediaType.APPLICATION_JSON).body(requestDto,ProfileRequestDto.class).);
        // Perform the mock HTTP POST request
//        mockMvc.perform(post("/create/profile")
//                        .contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(requestDto))
//                .andExpect(status().isAccepted()));
//                .andExpect(jsonPath("<span class="math-inline">\.resCode"\)\.value\("200"\)\)
//\.andExpect\(jsonPath\("</span>.resMessage").value("Successful"))
//                .andExpect(jsonPath("$.customerId").value(profile.getId().toString()));
//                .andExpect(jsonPath("<span class="math-inline">\.resCode"\)\.value\("200"\)\)
//\.andExpect\(jsonPath\("</span>.resMessage").value("Successful"))
//                .andExpect(jsonPath("$.customerId").value(profile.getId().toString()));
    }

//    @Test
//    void testCreateProfile_DuplicateLegalName_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate a duplicate legal name scenario
//        // Ensure the response contains the appropriate error status and message
//        ProfileRequestDto requestDto = createValidProfileRequest();
//        Mockito.when(validateProfileService.preRequestCreationValidations(requestDto)).thenReturn(false);
//        Mockito.when(errorCodesService.checkForFailure(validateProfileService.validateProfile(requestDto))).thenReturn(false);
//        when(createProfileTransformer.convertDtoToModel(requestDto)).thenReturn(createMockProfile());
////        doNothing().when(profileService).saveProfile(any());
//
////        verify(profileService, times(1)).saveProfile(any());
//        Profile profile = new Profile();
//        profile.setId(UUID.fromString("9e1d5153-4db9-4f97-a5ee-acf917617486"));
//        profile.setCompanyname(requestDto.getCompanyName());
//        profile.setLegalname(requestDto.getLegalName());
//        profile.setEmail(requestDto.getEmail());
//        profile.setWebsite(requestDto.getWebsite());
//
////        profile.setBusinessaddress(createProfileTransformer.con(requestDto.getBusinessAddress()));
////        profile.setLegaladdress(createProfileTransformer.convertDtoToModel(requestDto.getLegalAddress()));
////        profile.setPandetails(createProfileTransformer.convertDtoToModel(requestDto.getTaxidentifiers()));
////        profile.setEindetails(createProfileTransformer.convertDtoToModel(requestDto.getTaxidentifiers()));
//        Mockito.when(profileService.saveProfile(profile)).thenReturn(profile);
//        ResponseEntity<CreateProfileResponseDto> response = restTemplate.postForEntity("http://localhost:"+port+"/create/profile",requestDto,CreateProfileResponseDto.class,new HashMap<String,String>());
//        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
//        assertNotNull(response.getBody());
//    }

//    @Test
//    void testCreateProfile_ValidationFailure_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate a validation failure scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_ValidRequestWithEmptyOptionalFields_ReturnsAccepted() {
//        // Implement test with a valid request with empty optional fields
//        // Ensure the response is accepted and contains the expected data
//    }
//
//    @Test
//    void testCreateProfile_InvalidPAN_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate an invalid PAN scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_InvalidEIN_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate an invalid EIN scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_InvalidZIPCode_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate an invalid ZIP code scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_ValidRequestWithAdditionalData_ReturnsAccepted() {
//        // Implement test with a valid request with additional data
//        // Ensure the response is accepted and contains the expected data
//    }
//
//    @Test
//    void testCreateProfile_InvalidWebsiteURL_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate an invalid website URL scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_InvalidEmailAddress_ReturnsErrorResponse() {
//        // Mock the necessary services to simulate an invalid email address scenario
//        // Ensure the response contains the appropriate error status and message
//    }
//
//    @Test
//    void testCreateProfile_ValidRequestWithLine2Empty_ReturnsAccepted() {
//        // Implement test with a valid request with the optional field 'line2' empty
//        // Ensure the response is accepted and contains the expected data
//    }

    private ProfileRequestDto createValidProfileRequest() {
        ProfileRequestDto.AddressDTO businessAddress = new ProfileRequestDto.AddressDTO();
        businessAddress.setLine1("123 Main St");
        businessAddress.setCity("City");
        businessAddress.setState("State");
        businessAddress.setZip("12345");
        businessAddress.setCountry("Country");

        ProfileRequestDto.AddressDTO legalAddress = new ProfileRequestDto.AddressDTO();
        legalAddress.setLine1("456 Oak St");
        legalAddress.setCity("City");
        legalAddress.setState("State");
        legalAddress.setZip("67890");
        legalAddress.setCountry("Country");

        ProfileRequestDto.TaxIdentifiersDTO taxIdentifiers = new ProfileRequestDto.TaxIdentifiersDTO();
        taxIdentifiers.setPan("ABCDE1234F");
        taxIdentifiers.setEin("12-3456789-0");

        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setCompanyName("Company XYZ");
        requestDto.setLegalName("Legal XYZ");
        requestDto.setBusinessAddress(businessAddress);
        requestDto.setLegalAddress(legalAddress);
        requestDto.setTaxIdentifiers(taxIdentifiers);
        requestDto.setEmail("test@example.com");
        requestDto.setWebsite("http://www.example.com");

        return requestDto;
    }

    private Profile createMockProfile() {
        Profile profile = new Profile();
        // Set profile data as needed for testing
        return profile;
    }
}

