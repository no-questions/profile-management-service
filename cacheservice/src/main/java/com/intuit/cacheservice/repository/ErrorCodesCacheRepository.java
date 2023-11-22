package com.intuit.cacheservice.repository;

import com.intuit.cacheservice.models.ErrorCodesCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorCodesCacheRepository extends CrudRepository<ErrorCodesCache, String> {
    ErrorCodesCache findByErrorcode(String errorcode);

    List<ErrorCodesCache> findByIsfailure(Boolean isfailure);

    List<ErrorCodesCache> findByIsretryeligible(Boolean isretryeligible);

    List<ErrorCodesCache> findAll();
}
