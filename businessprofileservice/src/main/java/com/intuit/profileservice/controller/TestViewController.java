package com.intuit.profileservice.controller;

import com.intuit.profileservice.dto.UpdateProfileRequestDto;
import com.intuit.profileservice.models.Profile;
import com.intuit.profileservice.service.HandlingService;
import com.intuit.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.intuit.profileservice.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(VIEW_CONTROLLER_PATH)
public class TestViewController {

    private final ProfileService profileService;
    private final HandlingService handlingService;

    @CrossOrigin
    @GetMapping(VIEW_ALL_ENDPOINT)
    public ResponseEntity<List<UpdateProfileRequestDto>> viewAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        List<UpdateProfileRequestDto> dtoRes = new ArrayList<>();
        profiles.forEach(profile -> {
            dtoRes.add(profileService.convertProfileToUpdateRequest(profile));
        });
        return ResponseEntity.ok(dtoRes);
    }

    /**
     * Handles HTTP GET request to fetch a profile
     *
     * @param customerId RequestHeaders contain the id the of customer
     * @return ResponseEntity containing the profile of the customer
     */
    @CrossOrigin
    @GetMapping(VIEW_PROFILE_ENDPOINT)
    public ResponseEntity<UpdateProfileRequestDto> viewProfileById(@RequestHeader("id") String customerId) {
        Optional<Profile> optionalProfile = profileService.getProfileById(UUID.fromString(customerId));
        if (optionalProfile.isEmpty())
            handlingService.handleNoRecordFound();
        return ResponseEntity.ok(profileService.convertProfileToUpdateRequest(optionalProfile.get()));
    }

}
