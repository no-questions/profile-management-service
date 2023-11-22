//package com.intuit.profileservice.config;
//
//import com.intuit.profileservice.service.HandlingService;
//import com.intuit.profileservice.service.ValidateProfileService;
//import com.intuit.profileservice.service.impl.ErrorCodesServiceImpl;
//import com.intuit.profileservice.transformer.CreateProfileTransformer;
//import org.mockito.Mockito;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TestConfig {
//
//    @Bean
//    public ValidateProfileService mockValidateProfileService() {
//        return Mockito.mock(ValidateProfileService.class);
//    }
//
//    @Bean
//    public CreateProfileTransformer mockCreateProfileTransformer() {
//        return Mockito.mock(CreateProfileTransformer.class);
//    }
//
//    @Bean
//    public ErrorCodesServiceImpl mockErrorCodesServiceImpl() {
//        return Mockito.mock(ErrorCodesServiceImpl.class);
//    }
//
//    @Bean
//    public HandlingService mockHandlingService() {
//        return Mockito.mock(HandlingService.class);
//    }
//}