 package com.intuit.profileservice.transformers;

 import com.intuit.profileservice.dto.UpdateProfileRequestDto;
 import com.intuit.profileservice.exceptions.ApplicationException;
 import com.intuit.profileservice.models.Address;
 import com.intuit.profileservice.models.Profile;
 import com.intuit.profileservice.models.TaxIdentifier;
 import com.intuit.profileservice.transformer.CreateProfileTransformer;
 import com.intuit.profileservice.transformer.UpdateProfileTransformer;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.Mockito;
 import org.mockito.MockitoAnnotations;
 import org.slf4j.Logger;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.boot.test.mock.mockito.MockBean;

 import java.util.UUID;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.*;

 @SpringBootTest
 class UpdateProfileTransformerTest {

  @Mock
  private CreateProfileTransformer createProfileTransformer;

  @InjectMocks
  private UpdateProfileTransformer updateProfileTransformer;

  @Test
  void testConvertDtoToModel() {
   UpdateProfileRequestDto dto = createSampleUpdateProfileDto();
   Profile profile = createSampleExistingProfile();
   when(createProfileTransformer.convertDtoToModel(Mockito.any())).thenReturn(new Profile());
//   UpdateProfileTransformer transformer = new UpdateProfileTransformer(createProfileTransformer);
   Profile updatedProfile = updateProfileTransformer.convertDtoToModel(dto, profile);
   Assertions.assertEquals("New Company", updatedProfile.getCompanyname());
   Assertions.assertEquals("New Legal Name", updatedProfile.getLegalname());
   Assertions.assertEquals("new.email@example.com", updatedProfile.getEmail());
   Assertions.assertEquals("http://www.newwebsite.com", updatedProfile.getWebsite());

   Address businessAddress = updatedProfile.getBusinessaddress();
   Assertions.assertEquals("123 Main St", businessAddress.getLine1());
   Assertions.assertEquals("Suite 200", businessAddress.getLine2());
   Assertions.assertEquals("Anytown", businessAddress.getCity());
   Assertions.assertEquals("CA", businessAddress.getState());
   Assertions.assertEquals("94043", businessAddress.getZip());
   Assertions.assertEquals("USA", businessAddress.getCountry());

   Address legalAddress = updatedProfile.getLegaladdress();
   Assertions.assertEquals("456 Elm St", legalAddress.getLine1());
   Assertions.assertEquals("Apt 1", legalAddress.getLine2());
   Assertions.assertEquals("Anytown", legalAddress.getCity());
   Assertions.assertEquals("CA", legalAddress.getState());
   Assertions.assertEquals("94043", legalAddress.getZip());
   Assertions.assertEquals("USA", legalAddress.getCountry());

   TaxIdentifier panIdentifier = updatedProfile.getPandetails();
//   Assertions.assertEquals(TaxIdentifier.TaxIDType. /PAN, panIdentifier.getType());
   Assertions.assertEquals("123456789", panIdentifier.getIdentifier());

   TaxIdentifier einIdentifier = updatedProfile.getEindetails();
//   Assertions.assertEquals(TaxIdentifier.TaxIDType.EIN, einIdentifier.getType());
   Assertions.assertEquals("987654321", einIdentifier.getIdentifier());

   Assertions.assertTrue(updatedProfile.getIsmodified());
   Assertions.assertNotNull(updatedProfile.getModifieddate());
  }

//  @Test
//  void testConvertDtoToModel_NoChanges() {
//   UpdateProfileRequestDto dto = createSampleUpdateProfileDto();
//   Profile profile = createSampleExistingProfile();
//   when(createProfileTransformer.convertDtoToModel(Mockito.any())).thenReturn(new Profile());
//
//
////   UpdateProfileTransformer transformer = new UpdateProfileTransformer(createProfileTransformer);
//   Profile updatedProfile = updateProfileTransformer.convertDtoToModel(dto, profile);
//
//   Assertions.assertThrows(ApplicationException.class, () -> {
//    updateProfileTransformer.convertDtoToModel(dto, updatedProfile);
//   });
//  }

  @Test
  public void testConvertDtoToModel_NullAddress() {
   UpdateProfileRequestDto dto = createSampleUpdateProfileDto();
   dto.setBusinessAddress(null);
   Profile profile = createSampleExistingProfile();
   when(createProfileTransformer.convertDtoToModel(Mockito.any())).thenReturn(new Profile());


//   UpdateProfileTransformer transformer = new UpdateProfileTransformer(createProfileTransformer);
   Profile updatedProfile = updateProfileTransformer.convertDtoToModel(dto, profile);

   Assertions.assertNull(updatedProfile.getBusinessaddress());
  }

  @Test
  void convertDtoToModel_NoChanges_ExceptionThrown() {
   // Arrange
   UpdateProfileRequestDto dto = createSampleUpdateProfileDto();
   Profile existingProfile = createSampleExistingProfile();
   when(createProfileTransformer.convertDtoToModel(dto)).thenReturn(existingProfile);

   // Act and Assert
   ApplicationException exception = assertThrows(ApplicationException.class, () ->
           updateProfileTransformer.convertDtoToModel(dto, existingProfile));

   assertEquals("UC", exception.getErrorCode());
//   assertEquals("No changes in the profile", exception.getMessage());
//   verifyZeroInteractions(logger);
  }

  @Test
  void convertDtoToModel_HandleConversionException() {
   // Arrange
   UpdateProfileRequestDto dto = createSampleUpdateProfileDto();
   Profile existingProfile = createSampleExistingProfile();
   when(createProfileTransformer.convertDtoToModel(dto)).thenThrow(new RuntimeException("Simulated error"));

   // Act and Assert
   RuntimeException exception = assertThrows(RuntimeException.class, () ->
           updateProfileTransformer.convertDtoToModel(dto, existingProfile));

   assertEquals("Simulated error", exception.getMessage());
//   verify(logger, times(1)).error(anyString(), eq("convertDtoToModel"), any(RuntimeException.class));
//   verify(logger, times(1)).debug("Exiting convertDtoToModel method");
  }

  @Test
  void convertModelToDto_SuccessfullyConvertsModelToDto() {
   // Arrange
   Profile profile = createSampleExistingProfile();

   // Act
   UpdateProfileRequestDto dto = updateProfileTransformer.convertModelToDto(profile);

   // Assert
   assertNotNull(dto);
   assertEquals(profile.getId().toString(), dto.getCustomerId());
   assertEquals(profile.getCompanyname(), dto.getCompanyName());
   assertEquals(profile.getLegalname(), dto.getLegalName());
   assertEquals(profile.getEmail(), dto.getEmail());
   assertEquals(profile.getWebsite(), dto.getWebsite());
//   verifyZeroInteractions(logger);
  }

  // Helper methods to create sample objects for testing

  private UpdateProfileRequestDto createSampleUpdateProfileDto() {
   UpdateProfileRequestDto dto = new UpdateProfileRequestDto();
   dto.setCustomerId(UUID.randomUUID().toString());
   dto.setCompanyName("New Company");
   dto.setLegalName("New Legal Name");
   dto.setEmail("new.email@example.com");
   dto.setWebsite("http://www.newwebsite.com");

   // Set business address
   UpdateProfileRequestDto.AddressDTO businessAddressDto = new UpdateProfileRequestDto.AddressDTO();
   businessAddressDto.setLine1("123 Main St");
   businessAddressDto.setLine2("Suite 200");
   businessAddressDto.setCity("Anytown");
   businessAddressDto.setState("CA");
   businessAddressDto.setZip("94043");
   businessAddressDto.setCountry("USA");
   dto.setBusinessAddress(businessAddressDto);

   // Set legal address
   UpdateProfileRequestDto.AddressDTO legalAddressDto = new UpdateProfileRequestDto.AddressDTO();
   legalAddressDto.setLine1("456 Elm St");
   legalAddressDto.setLine2("Apt 1");
   legalAddressDto.setCity("Anytown");
   legalAddressDto.setState("CA");
   legalAddressDto.setZip("94043");
   legalAddressDto.setCountry("USA");
   dto.setLegalAddress(legalAddressDto);

   // Set tax identifiers
   UpdateProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto = new UpdateProfileRequestDto.TaxIdentifiersDTO();
   taxIdentifiersDto.setPan("123456789");
   taxIdentifiersDto.setEin("987654321");
   dto.setTaxIdentifiers(taxIdentifiersDto);

   // Set other properties...

   return dto;
  }


  private Profile createSampleExistingProfile() {
   Profile profile = new Profile();
   profile.setId(UUID.randomUUID());
   profile.setCompanyname("Company");
   profile.setLegalname("Legal Name");
   profile.setEmail("email@example.com");
   profile.setWebsite("http://www.website.com");
   // Set other properties...
   return profile;
  }
 }

