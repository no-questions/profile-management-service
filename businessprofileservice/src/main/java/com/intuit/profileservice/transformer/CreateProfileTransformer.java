package com.intuit.profileservice.transformer;

import com.intuit.profileservice.dto.ProfileRequestDto;
import com.intuit.profileservice.exceptions.BadRequestException;
import com.intuit.profileservice.models.Address;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.models.TaxIdentifier;
import com.intuit.profileservice.models.TaxIdentifier.TaxIDType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateProfileTransformer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Profile convertDtoToModel(ProfileRequestDto dto) {
        logger.debug("Entering convertDtoToModel method");

        try {
            Profile profile = new Profile();

            // Set simple properties
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
            profile.setIsmodified(false);

            logger.debug("Profile successfully converted from DTO to Model");
            return profile;
        } catch (Exception e) {
            handleConversionException("convertDtoToModel", e);
            throw e; // Rethrow the exception after logging
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
            throw e; // Rethrow the exception after logging
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
            throw e; // Rethrow the exception after logging
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
            throw e; // Rethrow the exception after logging
        } finally {
            logger.debug("Exiting convertEinDtoToModel method");
        }
    }

    private void handleConversionException(String methodName, Exception e) {
        logger.error("{}: Error occurred during conversion: {}", methodName, e.getMessage());
    }
}

