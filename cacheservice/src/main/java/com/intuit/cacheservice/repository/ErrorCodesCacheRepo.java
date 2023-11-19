// package com.intuit.cacheservice.repository;

// import com.intuit.cacheservice.models.ErrorCodesCache;
// import jakarta.annotation.PostConstruct;
// import lombok.NonNull;
// import lombok.RequiredArgsConstructor;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.HashOperations;
// import org.springframework.stereotype.Repository;

// import java.util.Map;
// import java.util.stream.Collectors;

// @Repository
// @RequiredArgsConstructor
// public class ErrorCodesCacheRepo implements RedisRepository {

//     private static final String KEY = "ErrorCodes";

//     @NonNull
//     @Autowired
//     private final HashOperations<String, String, ErrorCodesCache> hashOperations;

//     // @PostConstruct
//     // private void init() {
//     //     hashOperations = redisTemplate.opsForHash();
//     // }

//     @Override
//     public Map<String, ErrorCodesCache> findAllErrorCodes() {
//         return hashOperations.entries(KEY);
//     }

//     @Override
//     public void add(ErrorCodesCache errorCode) {
//         hashOperations.put(KEY, errorCode.getErrorcode(), errorCode);
//     }

//     @Override
//     public void delete(String errorCode) {
//         hashOperations.delete(KEY, errorCode);
//     }

//     @Override
//     public ErrorCodesCache findByErrorCode(String errorCode) {
//         return hashOperations.get(KEY, errorCode);
//     }

//     @Override
//     public void deleteAll() {
//         hashOperations
//                 .entries(KEY).keySet().forEach(haskKey -> hashOperations.delete(KEY, haskKey));
//     }

//     @Override
//     public void saveAll(Iterable<ErrorCodesCache> data) {
//         data.forEach(currObj -> hashOperations.put(KEY, currObj.getErrorcode(), currObj));
//     }

//     @Override
//     public Map<String, ErrorCodesCache> findAllNotFailureErrorCodes() {
//         // TODO Auto-generated method stub
//         Map<String, ErrorCodesCache> allErrorCodes = findAllErrorCodes();
//         return allErrorCodes.entrySet().stream()
//                 .filter(entry -> entry.getValue().getIsretryeligible())
//                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//     }

// }
