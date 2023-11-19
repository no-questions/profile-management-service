package com.intuit.profileservice.repository;

import com.intuit.profileservice.models.TaxIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxIdentifierRepository extends JpaRepository<TaxIdentifier, Long> {

}
