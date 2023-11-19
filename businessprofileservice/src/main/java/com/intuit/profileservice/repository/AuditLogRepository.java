package com.intuit.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intuit.profileservice.models.AuditLog;

@Repository
public interface AuditLogRepository extends  JpaRepository<AuditLog, Long> {
    
}
