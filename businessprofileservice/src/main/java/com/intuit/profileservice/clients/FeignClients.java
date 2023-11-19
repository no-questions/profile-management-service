// package com.intuit.profileservice.clients;

// import org.springframework.beans.factory.ObjectFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.cloud.openfeign.support.SpringEncoder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import com.intuit.profileservice.dto.BaseResponse;
// import com.intuit.profileservice.dto.CreateProfileRequestDto;

// // @FeignClient(name="validate-service-client", url="http://localhost:1235", configuration = FeignClients.ClientConfiguration.class)
// @FeignClient(name="validate-service-client", url="http://localhost:1235")
// public interface FeignClients {


//     @PostMapping( value = "/create/validateprofile" ,consumes = MediaType.APPLICATION_JSON_VALUE)
//     ResponseEntity<BaseResponse> validateCreation(@RequestBody CreateProfileRequestDto req);

//     // @Configuration
//     // public static class ClientConfiguration {
//     //     @Autowired
//     //     private ObjectFactory<HttpMessageConverters> messageConverters;

//     //     @Bean
//     //     public Encoder feignEncoder() {
//     //         return new SpringFormEncoder(new SpringEncoder(messageConverters));
//     //     }
//     // }
// }
