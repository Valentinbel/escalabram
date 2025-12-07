package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.service.ProfileService;
import com.escalabram.escalabram.service.dto.ProfileDTO;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;

    @GetMapping("/profiles/users/{userId}")
    public ResponseEntity<ProfileDTO> getProfileByUserId(@PathVariable Long userId) {
        log.info("REST request to get profile from userId: {}", userId);
        try {
            Optional<ProfileDTO> profileDTO = profileService.findByUserId(userId);
            return ResponseUtil.wrapOrNotFound(profileDTO);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data in getProfileByUserId",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Used for create and update
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDTO> saveProfile(@Valid @RequestBody ProfileDTO profileDTO){
        log.info("REST request to save Profile of userid: {}", profileDTO.getUserId());
        try {
            ProfileDTO createdProfileDTO = profileService.saveProfile(profileDTO);
            return new ResponseEntity<>(createdProfileDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}