package com.intuit.cacheservice.repository;

import com.intuit.cacheservice.models.ErrorCodesCache;

import java.util.Map;

public interface RedisRepository {
    Map<String, ErrorCodesCache> findAllErrorCodes();

    void add(ErrorCodesCache errorCode);

    void delete(String id);

    ErrorCodesCache findByErrorCode(String errorCode);

    void deleteAll();

    void saveAll(Iterable<ErrorCodesCache> data);

    Map<String, ErrorCodesCache> findAllNotFailureErrorCodes();

}
