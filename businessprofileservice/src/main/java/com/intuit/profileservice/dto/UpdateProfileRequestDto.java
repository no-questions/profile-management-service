package com.intuit.profileservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequestDto extends ProfileRequestDto {

    @JsonProperty("customerId")
    @NotBlank
    private String customerId;

    // @NotBlank(message = "Company name cannot be blank")
    // @JsonProperty("companyName")
    // private String companyName;

    // @NotBlank(message = "Legal name cannot be blank")
    // @JsonProperty("legalName")
    // private String legalName;

    // @Valid
    // @JsonProperty("businessAddress")
    // private AddressDTO businessAddress;

    // @Valid
    // @JsonProperty("legalAddress")
    // private AddressDTO legalAddress;

    // @Valid
    // @JsonProperty("taxIdentifiers")
    // private TaxIdentifiersDTO taxIdentifiers;

    // @Email(message = "Please enter a valid email address")
    // @NotBlank(message = "Email cannot be blank")
    // @JsonProperty("email")
    // private String email;

    // @Pattern(regexp = "^(http|https)://.*$", message = "Please enter a valid website URL")
    // @NotBlank(message = "Website cannot be blank")
    // @JsonProperty("website")
    // private String website;

    // // Constructors, getters, and setters go here

    // // Inner class for AddressDTO
    // @Data
    // public static class AddressDTO {

    //     @NotBlank(message = "Address line 1 cannot be blank")
    //     @JsonProperty("line1")
    //     private String line1;

    //     @JsonProperty("line2")
    //     private String line2;

    //     @NotBlank(message = "City cannot be blank")
    //     @JsonProperty("city")
    //     private String city;

    //     @NotBlank(message = "State cannot be blank")
    //     @JsonProperty("state")
    //     private String state;

    //     @NotBlank(message = "ZIP code cannot be blank")
    //     @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$", message = "Please enter a valid ZIP code")
    //     @JsonProperty("zip")
    //     private String zip;

    //     @NotBlank(message = "Country cannot be blank")
    //     @JsonProperty("country")
    //     private String country;

    //     // Constructors, getters, and setters go here
    // }

    // // Inner class for TaxIdentifiersDTO
    // @Data
    // public static class TaxIdentifiersDTO {
    //     @NotBlank(message = "PAN cannot be blank")
    //     @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Please enter a valid PAN")
    //     @JsonProperty("pan")
    //     private String pan;

    //     @NotBlank(message = "EIN cannot be blank")
    //     @Pattern(regexp = "^\\d{2}-\\d{7}-\\d{1}$", message = "Please enter a valid EIN")
    //     @JsonProperty("ein")
    //     private String ein;

    //     // Constructors, getters, and setters go here
    // }
}
