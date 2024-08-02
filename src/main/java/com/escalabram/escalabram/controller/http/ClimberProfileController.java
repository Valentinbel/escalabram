package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.controller.errors.BadRequestAlertException;
import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.service.ClimberProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ClimberProfileController {

    private final Logger log = LoggerFactory.getLogger(ClimberProfileController.class);
    private final ClimberProfileService climberProfileService;

    public ClimberProfileController(ClimberProfileService climberProfileService) {
        this.climberProfileService = climberProfileService;
    }


    @GetMapping("/climber-profiles")
    public ResponseEntity<List<ClimberProfile>> getAllClimberProfiles(){
        log.debug("REST request to get list of All ClimberProfiles");
        try{
            List<ClimberProfile> climberProfiles = climberProfileService.findAll();
            return ResponseEntity.ok(climberProfiles);
        } catch (Exception e){
            log.error("An error was encountered while retrieving data from ClimberProfile",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/climber-profile")
    public ResponseEntity<ClimberProfile> createClimberProfile(@Valid @RequestBody ClimberProfile climberProfile){
        log.debug("REST request to save ClimberProfile : {}", climberProfile);
        try {
            if (climberProfile.getId() != null)
                throw new BadRequestAlertException("A new climberProfile cannot already have an ID");

            //Remplacer ClimberProfile par un DTO ///////////////////////////////////
            climberProfileService.createClimberProfile(climberProfile);

            return ResponseEntity.status(HttpStatus.OK).build();
            //return ResponseEntity.created(new URI("/api/personne-autorises/" + result.getId())).body(result);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}