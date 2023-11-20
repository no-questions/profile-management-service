package com.intuit.profileservice.repository;


import com.intuit.profileservice.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByLegalname(String legalName);
    Optional<Profile> findByIdAndLegalname(UUID id, String legalName);
}
