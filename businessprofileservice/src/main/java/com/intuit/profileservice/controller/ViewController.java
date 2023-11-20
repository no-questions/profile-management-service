package com.intuit.profileservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ViewController {
    
    private final ProfileService profileService;

    @GetMapping("/view/all")
    public ResponseEntity<List<ProfileRequestDto>> viewAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        List<ProfileRequestDto> dtoRes = new ArrayList<>();
        profiles.forEach(profile->dtoRes.add(convertModelToDto(profile)));
        return ResponseEntity.ok(dtoRes);
    }

    public ProfileRequestDto convertModelToDto(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }

        ProfileRequestDto dto = new ProfileRequestDto();
        dto.setCompanyName(profile.getCompanyname());
        dto.setLegalName(profile.getLegalname());
        dto.setEmail(profile.getEmail());
        dto.setWebsite(profile.getWebsite());

        if (profile.getBusinessaddress() != null) {
            dto.setBusinessAddress(convertAddressModelToDto(profile.getBusinessaddress()));
        }

        if (profile.getLegaladdress() != null) {
            dto.setLegalAddress(convertAddressModelToDto(profile.getLegaladdress()));
        }

        if (profile.getPandetails() != null || profile.getEindetails() != null) {
            dto.setTaxIdentifiers(convertTaxIdentifiersModelToDto(profile));
        }

        return dto;
    }

    private ProfileRequestDto.AddressDTO convertAddressModelToDto(Address addressModel) {
        if (addressModel == null) {
            throw new IllegalArgumentException("Address model cannot be null");
        }

        ProfileRequestDto.AddressDTO addressDto = new ProfileRequestDto.AddressDTO();
        addressDto.setLine1(addressModel.getLine1());
        addressDto.setLine2(addressModel.getLine2());
        addressDto.setCity(addressModel.getCity());
        addressDto.setState(addressModel.getState());
        addressDto.setZip(addressModel.getZip());
        addressDto.setCountry(addressModel.getCountry());
        return addressDto;
    }

    private ProfileRequestDto.TaxIdentifiersDTO convertTaxIdentifiersModelToDto(Profile profile) {
        ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto = new ProfileRequestDto.TaxIdentifiersDTO();

        if (profile.getPandetails() != null) {
            taxIdentifiersDto.setPan(profile.getPandetails().getIdentifier());
        }

        if (profile.getEindetails() != null) {
            taxIdentifiersDto.setEin(profile.getEindetails().getIdentifier());
        }

        return taxIdentifiersDto;
    }
}
