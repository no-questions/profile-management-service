package com.intuit.validationservice.controller;

import com.intuit.validationservice.Service.InvokeProductCalls;
import com.intuit.validationservice.dto.BaseResponse;
import com.intuit.validationservice.dto.CreateValidateDto;
import com.intuit.validationservice.dto.ValidateProfileResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
class ProfileValidationControllerTest {

    @Mock
    private InvokeProductCalls invokeProductCalls;
    @Mock
    private Map<String, String> mapResCodes;

    @InjectMocks
    private ProfileValidationController profileValidationController;

    @Test
    void testValidateProfileCreation() {
        CreateValidateDto request = createValidCreateValidateDto();
        ProfileValidationController controller = new ProfileValidationController(invokeProductCalls, null);

        BaseResponse productAResponse = new BaseResponse("00", "Product A validation successful");
        BaseResponse productBResponse = new BaseResponse("00", "Product B validation successful");
        BaseResponse productCResponse = new BaseResponse("00", "Product C validation successful");
        BaseResponse productDResponse = new BaseResponse("00", "Product D validation successful");

        when(invokeProductCalls.invokeProductA(request)).thenReturn(productAResponse);
        when(invokeProductCalls.invokeProductB(request)).thenReturn(productBResponse);
        when(invokeProductCalls.invokeProductC(request)).thenReturn(productCResponse);
        when(invokeProductCalls.invokeProductD(request)).thenReturn(productDResponse);

        ResponseEntity<?> response = controller.validateProfileCreation(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        ValidateProfileResponse validateProfileResponse = (ValidateProfileResponse) response.getBody();
        List<BaseResponse> responseList = validateProfileResponse.getRes();

        Assertions.assertEquals(4, responseList.size());
        Assertions.assertEquals(productAResponse, responseList.get(0));
        Assertions.assertEquals(productBResponse, responseList.get(1));
        Assertions.assertEquals(productCResponse, responseList.get(2));
        Assertions.assertEquals(productDResponse, responseList.get(3));
    }

    @Test
    public void testValidateProfileCreationWithInternalServerError() {
        CreateValidateDto request = createValidCreateValidateDto();
        ProfileValidationController controller = new ProfileValidationController(invokeProductCalls, null);

        when(invokeProductCalls.invokeProductA(request)).thenThrow(new RuntimeException("Product A validation failed"));

        ResponseEntity<?> response = controller.validateProfileCreation(request);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Something went wrong", response.getBody());
    }


    public static CreateValidateDto createValidCreateValidateDto() {
        CreateValidateDto createValidateDto = new CreateValidateDto();
        createValidateDto.setCompanyName("Sample Company");
        createValidateDto.setLegalName("Sample Legal Name");

        // Set business address
        CreateValidateDto.AddressDTO businessAddress = new CreateValidateDto.AddressDTO();
        businessAddress.setLine1("123 Main St");
        businessAddress.setCity("Sample City");
        businessAddress.setState("CA");
        businessAddress.setZip("12345");
        businessAddress.setCountry("Sample Country");
        createValidateDto.setBusinessAddress(businessAddress);

        // Set legal address
        CreateValidateDto.AddressDTO legalAddress = new CreateValidateDto.AddressDTO();
        legalAddress.setLine1("456 Elm St");
        legalAddress.setCity("Another City");
        legalAddress.setState("NY");
        legalAddress.setZip("54321");
        legalAddress.setCountry("Another Country");
        createValidateDto.setLegalAddress(legalAddress);

        // Set tax identifiers
        CreateValidateDto.TaxIdentifiersDTO taxIdentifiers = new CreateValidateDto.TaxIdentifiersDTO();
        taxIdentifiers.setPan("ABCDE1234F");
        taxIdentifiers.setEin("12-3456789-1");
        createValidateDto.setTaxIdentifiers(taxIdentifiers);

        createValidateDto.setEmail("sample@example.com");
        createValidateDto.setWebsite("http://www.sample.com");

        return createValidateDto;
    }
    @Test
    void testSetRescode() {
        when(mapResCodes.putIfAbsent(Mockito.anyString(),Mockito.anyString())).thenReturn("okay");
        ResponseEntity<Boolean> response = profileValidationController.setRescode("A", "B", "C", "D");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody());
    }
}