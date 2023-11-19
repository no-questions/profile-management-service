package com.intuit.cacheservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intuit.cacheservice.models.ErrorCodesCache;
import java.util.List;

@Repository
public interface ErrorCodesCacheRepository extends CrudRepository<ErrorCodesCache, String> {
    ErrorCodesCache findByErrorcode(String errorcode);
    List<ErrorCodesCache> findByIsfailure(Boolean isfailure);
    List<ErrorCodesCache> findByIsretryeligible(Boolean isretryeligible);
}
