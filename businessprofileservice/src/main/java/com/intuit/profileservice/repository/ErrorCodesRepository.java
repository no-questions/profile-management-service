package com.intuit.profileservice.repository;

import com.intuit.profileservice.models.ErrorCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCodesRepository extends JpaRepository<ErrorCodes, String> {

    ErrorCodes findByErrorcode(String errorCode);

}
