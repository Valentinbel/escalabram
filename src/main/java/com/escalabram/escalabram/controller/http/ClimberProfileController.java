package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
public class ClimberProfileController {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileController.class);
    private final ClimberProfileService climberProfileService;

    public ClimberProfileController(ClimberProfileService climberProfileService) {
        this.climberProfileService = climberProfileService;
    }

    @GetMapping("/climber-profiles/climber-users/{climberUserId}")
    public ResponseEntity<ClimberProfileDTO> getClimberProfileById(@PathVariable Long climberUserId) {
        try {
            Optional<ClimberProfileDTO> climberProfileDTO = climberProfileService.findByClimberUserId(climberUserId);
            return ResponseUtil.wrapOrNotFound(climberProfileDTO);
        } catch (Exception e) {
        log.error("An error was encountered while retrieving data in getSearchByClimberProfileId",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/climber-profiles")
    public ResponseEntity<ClimberProfileDTO> createClimberProfile(@Valid @RequestBody ClimberProfileDTO climberProfileDTO){
        log.debug("REST request to save ClimberProfile : {}", climberProfileDTO);
        try {
            ClimberProfileDTO createdClimberProfileDTO = climberProfileService.createClimberProfile(climberProfileDTO);
            return new ResponseEntity<>(createdClimberProfileDTO, HttpStatus.CREATED);
            //return ResponseEntity.created(new URI("/api/personne-autorises/" + result.getId())).body(result);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}