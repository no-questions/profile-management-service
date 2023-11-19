package com.intuit.businessprofileservice.transformers;


import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.models.TaxIdentifier;
import com.intuit.profileservice.models.TaxIdentifier.TaxIDType;
import com.intuit.profileservice.transformer.CreateProfileTransformer;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class CreateProfileTransformerTest {


    @InjectMocks
    private CreateProfileTransformer createProfileTransformer;

    @Test
    public void testConvertDtoToModel_Success() {
        ProfileRequestDto createProfileRequestDto = new ProfileRequestDto();
        createProfileRequestDto.setCompanyName("Company");
        createProfileRequestDto.setLegalName("Legal Name");
        createProfileRequestDto.setEmail("test@example.com");
        createProfileRequestDto.setWebsite("http://example.com");

        ProfileRequestDto.AddressDTO businessAddressDto = new ProfileRequestDto.AddressDTO();
        businessAddressDto.setLine1("123 Main St");
        businessAddressDto.setCity("City");
        businessAddressDto.setState("State");
        businessAddressDto.setZip("12345");
        businessAddressDto.setCountry("Country");
        createProfileRequestDto.setBusinessAddress(businessAddressDto);

        ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto = new ProfileRequestDto.TaxIdentifiersDTO();
        taxIdentifiersDto.setPan("ABCDE1234F");
        taxIdentifiersDto.setEin("123456789");
        createProfileRequestDto.setTaxIdentifiers(taxIdentifiersDto);

        // Create an instance of the class under test
        CreateProfileTransformer createProfileTransformer = new CreateProfileTransformer();

        // Invoke the method to be tested
        Profile resultProfile = createProfileTransformer.convertDtoToModel(createProfileRequestDto);

        // Assertions
        assertNotNull(resultProfile);
        assertEquals("Company", resultProfile.getCompanyname());
        assertEquals("Legal Name", resultProfile.getLegalname());
        assertEquals("test@example.com", resultProfile.getEmail());
        assertEquals("http://example.com", resultProfile.getWebsite());

        Address resultBusinessAddress = resultProfile.getBusinessaddress();
        assertNotNull(resultBusinessAddress);
        assertEquals("123 Main St", resultBusinessAddress.getLine1());
        assertEquals("City", resultBusinessAddress.getCity());
        assertEquals("State", resultBusinessAddress.getState());
        assertEquals("12345", resultBusinessAddress.getZip());
        assertEquals("Country", resultBusinessAddress.getCountry());

        TaxIdentifier resultPanDetails = resultProfile.getPandetails();
        assertNotNull(resultPanDetails);
        assertEquals(TaxIDType.PAN.toString(), resultPanDetails.getType());
        assertEquals("ABCDE1234F", resultPanDetails.getIdentifier());

        TaxIdentifier resultEinDetails = resultProfile.getEindetails();
        assertNotNull(resultEinDetails);
        assertEquals(TaxIDType.EIN.toString(), resultEinDetails.getType());
        assertEquals("123456789", resultEinDetails.getIdentifier());
    }

}
