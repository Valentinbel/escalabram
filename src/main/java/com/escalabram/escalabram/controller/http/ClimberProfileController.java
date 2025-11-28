package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
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
public class ClimberProfileController {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileController.class);
    private final ClimberProfileService climberProfileService;

    @GetMapping("/climber-profiles/climber-users/{climberUserId}")
    public ResponseEntity<ClimberProfileDTO> getClimberProfileByClimberUserId(@PathVariable Long climberUserId) {
        log.info("REST request to get profile from userId: {}", climberUserId);
        try {
            Optional<ClimberProfileDTO> climberProfileDTO = climberProfileService.findByClimberUserId(climberUserId);
            return ResponseUtil.wrapOrNotFound(climberProfileDTO);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data in getSearchByClimberProfileId",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Used for create and update
    @PostMapping("/climber-profiles")
    public ResponseEntity<ClimberProfileDTO> saveClimberProfile(@Valid @RequestBody ClimberProfileDTO climberProfileDTO){
        log.info("REST request to save ClimberProfile of userid: {}", climberProfileDTO.getClimberUserId());
        try {
            ClimberProfileDTO createdClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTO);
            return new ResponseEntity<>(createdClimberProfileDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}