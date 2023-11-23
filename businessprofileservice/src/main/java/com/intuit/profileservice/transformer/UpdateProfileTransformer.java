package com.intuit.profileservice.transformer;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.exceptions.ApplicationException;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.models.TaxIdentifier;
import com.intuit.profileservice.models.TaxIdentifier.TaxIDType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProfileTransformer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CreateProfileTransformer createProfileTransformer;


//    private final ProfileRepository ProfileRepository;

    // public Profile convertDtoToModel(ProfileRequestDto dto) {
    // Optional<Profile> optProfiles =
    // profileService.getProfileByCompanyName(dto.getLegalName());
    // if (optProfiles.isEmpty()) {
    // throw new ApplicationException("DC","Duplicate legal name");
    // }

    // Profile profile = optProfiles.get();

    // // Set simple properties
    // profile.setLegalname(dto.getLegalName());
    // profile.setEmail(dto.getEmail());
    // profile.setWebsite(dto.getWebsite());

    // // Set business address
    // if (dto.getBusinessAddress() != null) {
    // profile.setBusinessaddress(convertAddressDtoToModel(dto.getBusinessAddress()));
    // }

    // // Set legal address
    // if (dto.getLegalAddress() != null) {
    // profile.setLegaladdress(convertAddressDtoToModel(dto.getLegalAddress()));
    // }

    // if (dto.getTaxIdentifiers() != null) {
    // profile.setPandetails(convertPanDtoToModel(dto.getTaxIdentifiers()));
    // profile.setEindetails(convertEinDtoToModel(dto.getTaxIdentifiers()));
    // }

    // profile.setIsmodified(true);
    // profile.setModifieddate(new Date());
    // return profile;
    // }

    // private Address convertAddressDtoToModel(ProfileRequestDto.AddressDTO
    // addressDto) {
    // Address address = new Address();
    // address.setLine1(addressDto.getLine1());
    // address.setLine2(addressDto.getLine2());
    // address.setCity(addressDto.getCity());
    // address.setState(addressDto.getState());
    // address.setZip(addressDto.getZip());
    // address.setCountry(addressDto.getCountry());
    // return address;
    // }

    // private TaxIdentifier
    // convertPanDtoToModel(ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto) {
    // TaxIdentifier panIdentifier = new TaxIdentifier();
    // panIdentifier.setType(TaxIDType.PAN.toString());
    // panIdentifier.setIdentifier(taxIdentifiersDto.getPan());
    // return panIdentifier;
    // }

    // private TaxIdentifier
    // convertEinDtoToModel(ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto) {
    // TaxIdentifier einIdentifier = new TaxIdentifier();
    // einIdentifier.setType(TaxIDType.EIN.toString());
    // einIdentifier.setIdentifier(taxIdentifiersDto.getEin());
    // return einIdentifier;
    // }

    public Profile convertDtoToModel(UpdateProfileRequestDto dto, Profile profile) {
        logger.debug("Entering convertDtoToModel method");

        try {
//            Optional<Profile> optProfiles = profileService.findByCustomeridAndLegalName(UUID.fromString(dto.getCustomerId()),dto.getLegalName());
//            if (optProfiles.isEmpty()) {
//                throw new ApplicationException("LCC", "Legal Name Cannot be changed");
//            }

//            Profile profile = optProfiles.get();

            // Set simple properties
            Profile newProfile = createProfileTransformer.convertDtoToModel(dto);
            newProfile.setId(UUID.fromString(dto.getCustomerId()));
            newProfile.setIsmodified(profile.getIsmodified());
            newProfile.setModifieddate(profile.getModifieddate());
            if(newProfile.equals(profile))
                throw new ApplicationException("UC","No changes in the profile");
            profile.setCompanyname(dto.getCompanyName());
            profile.setLegalname(dto.getLegalName());
            profile.setEmail(dto.getEmail());
            profile.setWebsite(dto.getWebsite());

            // Set business address
            if (dto.getBusinessAddress() != null) {
                profile.setBusinessaddress(convertAddressDtoToModel(dto.getBusinessAddress()));
            }

            // Set legal address
            if (dto.getLegalAddress() != null) {
                profile.setLegaladdress(convertAddressDtoToModel(dto.getLegalAddress()));
            }

            // Set tax identifiers
            if (dto.getTaxIdentifiers() != null) {
                profile.setPandetails(convertPanDtoToModel(dto.getTaxIdentifiers()));
                profile.setEindetails(convertEinDtoToModel(dto.getTaxIdentifiers()));
            }
            logger.debug("Profile successfully converted from DTO to Model");
            profile.setIsmodified(true);
            profile.setModifieddate(new Date());
            return profile;
        } catch (Exception e) {
            handleConversionException("convertDtoToModel", e);
            throw e;
        } finally {
            logger.debug("Exiting convertDtoToModel method");
        }
    }

    private Address convertAddressDtoToModel(ProfileRequestDto.AddressDTO addressDto) {
        logger.debug("Entering convertAddressDtoToModel method");

        try {
            if (addressDto == null) {
                throw new BadRequestException("BR", "Address DTO cannot be null");
            }

            Address address = new Address();
            address.setLine1(addressDto.getLine1());
            address.setLine2(addressDto.getLine2());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setZip(addressDto.getZip());
            address.setCountry(addressDto.getCountry());

            logger.debug("Address DTO successfully converted to Model");
            return address;
        } catch (Exception e) {
            handleConversionException("convertAddressDtoToModel", e);
            throw e;
        } finally {
            logger.debug("Exiting convertAddressDtoToModel method");
        }
    }

    private TaxIdentifier convertPanDtoToModel(ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto) {
        logger.debug("Entering convertPanDtoToModel method");

        try {
            if (taxIdentifiersDto == null) {
                throw new BadRequestException("BR", "Tax Identifiers DTO cannot be null");
            }

            TaxIdentifier panIdentifier = new TaxIdentifier();
            panIdentifier.setType(TaxIDType.PAN.toString());

            if (taxIdentifiersDto.getPan() == null || taxIdentifiersDto.getPan().isEmpty()) {
                throw new BadRequestException("BR", "PAN cannot be null or empty");
            }

            panIdentifier.setIdentifier(taxIdentifiersDto.getPan());
            logger.debug("PAN DTO successfully converted to Model");
            return panIdentifier;
        } catch (Exception e) {
            handleConversionException("convertPanDtoToModel", e);
            throw e;
        } finally {
            logger.debug("Exiting convertPanDtoToModel method");
        }
    }

    private TaxIdentifier convertEinDtoToModel(ProfileRequestDto.TaxIdentifiersDTO taxIdentifiersDto) {
        logger.debug("Entering convertEinDtoToModel method");

        try {
            if (taxIdentifiersDto == null) {
                throw new BadRequestException("BR", "Tax Identifiers DTO cannot be null");
            }

            TaxIdentifier einIdentifier = new TaxIdentifier();
            einIdentifier.setType(TaxIDType.EIN.toString());

            if (taxIdentifiersDto.getEin() == null || taxIdentifiersDto.getEin().isEmpty()) {
                throw new BadRequestException("BR", "EIN cannot be null or empty");
            }

            einIdentifier.setIdentifier(taxIdentifiersDto.getEin());
            logger.debug("EIN DTO successfully converted to Model");
            return einIdentifier;
        } catch (Exception e) {
            handleConversionException("convertEinDtoToModel", e);
            throw e;
        } finally {
            logger.debug("Exiting convertEinDtoToModel method");
        }
    }

    public UpdateProfileRequestDto convertModelToDto(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }

        UpdateProfileRequestDto dto = new UpdateProfileRequestDto();
        dto.setCustomerId(profile.getId().toString());
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

    private void handleConversionException(String methodName, Exception e) {
        logger.error("{}: Error occurred during conversion: {}", methodName, e.getMessage());
    }
}
