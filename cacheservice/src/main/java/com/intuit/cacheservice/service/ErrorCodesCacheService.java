package com.intuit.cacheservice.service;

import com.intuit.cacheservice.exceptions.ApplicationException;
import com.intuit.cacheservice.exceptions.BadRequestException;
import com.intuit.cacheservice.models.ErrorCodesCache;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorCodesCacheService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorCodesCacheRepository errorCodesCacheRepository;
    private final DataLoadingService dataLoadingService;



    public ErrorCodesCache getByErrorCode(String errorCode) {
        return errorCodesCacheRepository.findByErrorcode(errorCode);
//        return errorDescription != null ? errorDescription.getErrormessage() : null;
    }

    public List<ErrorCodesCache> getAllErrorCodes() {
        return errorCodesCacheRepository.findAll();
//        return errorDescription != null ? errorDescription.getErrormessage() : null;
    }

     @PostConstruct
    void refreshCache() {
        logger.info("Entering refreshCache method");

        try {
            errorCodesCacheRepository.saveAll(dataLoadingService.loadErrorCodeData());
            logger.info("Cache refreshed successfully");
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                logger.error(e.getMessage());
                throw new BadRequestException("BR", e.getMessage());
            } else {
                logger.error(e.getMessage());
                throw new ApplicationException("FE", e.getMessage());
            }
        } finally {
            logger.info("Exiting refreshCache method");
        }
    }

}
