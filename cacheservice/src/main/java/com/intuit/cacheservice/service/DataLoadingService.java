package com.intuit.cacheservice.service;

import com.intuit.cacheservice.models.ErrorCodesCache;

import java.util.List;

public interface DataLoadingService {
    List<ErrorCodesCache> loadErrorCodeData();
}
