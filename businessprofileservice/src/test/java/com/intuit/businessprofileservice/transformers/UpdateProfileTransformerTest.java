package com.intuit.businessprofileservice.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.models.TaxIdentifier;
import com.intuit.profileservice.models.TaxIdentifier.TaxIDType;
import com.intuit.profileservice.transformer.UpdateProfileTransformer;

class UpdateProfileTransformerTest {

    @InjectMocks
    private UpdateProfileTransformer updateProfileTransformer;

    @Test
    void convertDtoToModel_ValidDto_ShouldConvertSuccessfully() throws NotFoundException {
        // Arrange
        ProfileRequestDto dto = createValidUpdateProfileRequestDto();

        // Act
        Profile profile = updateProfileTransformer.convertDtoToModel(dto);

        // Assert
        assertNotNull(profile);
        assertEquals(dto.getLegalName(), profile.getLegalname());
        assertEquals(dto.getEmail(), profile.getEmail());
        assertEquals(dto.getWebsite(), profile.getWebsite());
        assertAddressEquals(dto.getBusinessAddress(), profile.getBusinessaddress());
        assertAddressEquals(dto.getLegalAddress(), profile.getLegaladdress());
        assertTaxIdentifierEquals(dto.getTaxIdentifiers().getPan(), profile.getPandetails());
        assertTaxIdentifierEquals(dto.getTaxIdentifiers().getEin(), profile.getEindetails());
        assertTrue(profile.getIsmodified());
        assertNotNull(profile.getModifieddate());
    }

    @Test
    void convertDtoToModel_MissingCompanyName_ShouldThrowNotFoundException() {
        // Arrange
        ProfileRequestDto dto = new ProfileRequestDto();

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> updateProfileTransformer.convertDtoToModel(dto));
    }

    @Test
    void convertDtoToModel_InvalidBusinessAddress_ShouldThrowIllegalArgumentException() {
        // Arrange
        ProfileRequestDto dto = createValidUpdateProfileRequestDto();
        dto.getBusinessAddress().setLine1(null);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> updateProfileTransformer.convertDtoToModel(dto));
    }

    @Test
    void convertDtoToModel_InvalidLegalAddress_ShouldThrowIllegalArgumentException() {
        // Arrange
        ProfileRequestDto dto = createValidUpdateProfileRequestDto();
        dto.getLegalAddress().setCountry(null);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> updateProfileTransformer.convertDtoToModel(dto));
    }

    @Test
    void convertDtoToModel_InvalidTaxIdentifierPAN_ShouldThrowIllegalArgumentException() {
        // Arrange
        ProfileRequestDto dto = createValidUpdateProfileRequestDto();
        dto.getTaxIdentifiers().setPan("invalid-pan");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> updateProfileTransformer.convertDtoToModel(dto));
    }

    @Test
    void convertDtoToModel_InvalidTaxIdentifierEIN_ShouldThrowIllegalArgumentException() {
        // Arrange
        ProfileRequestDto dto = createValidUpdateProfileRequestDto();
        dto.getTaxIdentifiers().setEin("invalid-ein");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> updateProfileTransformer.convertDtoToModel(dto));
    }

    private ProfileRequestDto createValidUpdateProfileRequestDto() {
        ProfileRequestDto dto = new ProfileRequestDto();
        dto.setCompanyName("Acme Corporation");
        dto.setLegalName("Acme Corporation Inc.");
        dto.setEmail("info@acmecorp.com");
        dto.setWebsite("www.acmecorp.com");

        ProfileRequestDto.AddressDTO businessAddress = new ProfileRequestDto.AddressDTO();
        businessAddress.setLine1("123 Main Street");
        businessAddress.setLine2("Suite 456");
        businessAddress.setCity("Anytown");
        businessAddress.setState("CA");
        businessAddress.setZip("98765");
        businessAddress.setCountry("US");
        dto.setBusinessAddress(businessAddress);

        ProfileRequestDto.AddressDTO legalAddress = new ProfileRequestDto.AddressDTO();
        legalAddress.setLine1("456 Elm Street");
        legalAddress.setLine2("Suite 789");
        legalAddress.setCity("Anytown");
        legalAddress.setState("CA");
        legalAddress.setZip("98765");
        legalAddress.setCountry("US");
        dto.setLegalAddress(legalAddress);

        ProfileRequestDto.TaxIdentifiersDTO taxIdentifiers = new ProfileRequestDto.TaxIdentifiersDTO();
        taxIdentifiers.setPan("123456789");
        taxIdentifiers.setEin("987654321");
        dto.setTaxIdentifiers(taxIdentifiers);

        return dto;
    }

    private void assertAddressEquals(ProfileRequestDto.AddressDTO expectedAddress, Address actualAddress) {
        assertEquals(expectedAddress.getLine1(), actualAddress.getLine1());
        assertEquals(expectedAddress.getLine2(), actualAddress.getLine2());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getZip(), actualAddress.getZip());
        assertEquals(expectedAddress.getCountry(), actualAddress.getCountry());
    }

    private void assertTaxIdentifierEquals(String expectedIdentifier, TaxIdentifier actualIdentifier) {
        assertEquals(expectedIdentifier, actualIdentifier.getIdentifier());
        assertEquals(TaxIDType.valueOf(actualIdentifier.getType()), TaxIDType.valueOf("PAN"));
    }
}
