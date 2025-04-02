package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.FilesStorageService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
public class ClimberProfileController {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileController.class);
    private final ClimberProfileService climberProfileService;
    private final FilesStorageService filesStorageService;

    public ClimberProfileController(ClimberProfileService climberProfileService, FilesStorageService filesStorageService) {
        this.climberProfileService = climberProfileService;
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/climber-profiles/climber-users/{climberUserId}")
    public ResponseEntity<ClimberProfileDTO> getClimberProfileByClimberUserId(@PathVariable Long climberUserId) {
        try {
            Optional<ClimberProfileDTO> climberProfileDTO = climberProfileService.findByClimberUserId(climberUserId);
            return ResponseUtil.wrapOrNotFound(climberProfileDTO);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data in getSearchByClimberProfileId",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {

            // On save le profil. Si c'est le premier post, on récupère le profilId et on peut s'occuper de l'avatar.

            // init correspondant au profileid?
            this.filesStorageService.init(); //passer en parametre l'id et voir si il peut nous retourner le path
            System.out.println("/////////////////////////////");
            System.out.println("file.getOriginalFilename: "+ file.getOriginalFilename());

            //Si existe dejà: message: "message": "Could not upload the file: Kavinski - NightCall.txt. Error: A file of that name already exists."
            //Verifier que le fichier avec le path existe pas dejà ?
            this.filesStorageService.save(file);

            //file a plein de getName et autres. Voir ce qui nous interesse ici.
            //Faire un save de fileInfo. Lui faire un repo et service.
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    // Used for create and update
    @PostMapping("/climber-profiles")
    public ResponseEntity<ClimberProfileDTO> saveClimberProfile(@Valid @RequestBody ClimberProfileDTO climberProfileDTO){
        log.debug("REST request to save ClimberProfile : {}", climberProfileDTO);
        try {
            ClimberProfileDTO createdClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTO);
            return new ResponseEntity<>(createdClimberProfileDTO, HttpStatus.CREATED);
            //return ResponseEntity.created(new URI("/api/personne-autorises/" + result.getId())).body(result);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}