package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.FilesStorageService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClimberProfileController {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileController.class);
    private final ClimberProfileService climberProfileService;
    private final FilesStorageService filesStorageService;

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

    ////`${this.baseUrl}climber-profiles/avatar`
    /// Depuis avatar.service. juste pour l'avatar et le userId
    @PostMapping("/climber-profiles/avatar")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userIdString) {
        try {
            // Mettre ce Endpoint dnas un Controller à part ??
            ///On renvoie un Long qui sera le FileInfoId qui nous servira pour mettre dans le profileId

            FileInfo fileInfo = this.filesStorageService.saveAvatar(file, userIdString);
            return ResponseEntity.status(HttpStatus.OK).body(fileInfo.getId());
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            log.error(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    // Used for create and update
    // TODO c'est celui ci qu'il faut adapter. pour recevoir le file comme plus haut
    @PostMapping("/climber-profiles")
    public ResponseEntity<ClimberProfileDTO> saveClimberProfile(@Valid @RequestBody ClimberProfileDTO climberProfileDTO){
        log.debug("REST request to save ClimberProfile : {}", climberProfileDTO);
        try {
            // TODO Comme tous mes appels sont liés, tout mettre dans un seul endpoint.
            //  Comme ca on appel le back qu'une seule fois.
            //  C'est le service qui va gérer ensuite.
            // => actualiser le username du user.
            // ==> l'avatar et recuper l'id pour le mettre dans le profile

//            if (file != null) // TODO : A mettre dans le service
//                climberProfileDTO.setFileInfo(filesStorageService.saveAvatar(file, climberProfileDTO.getClimberUserId()));

            log.info("climberProfileDTO: {}", climberProfileDTO);
            // TODO quoi faire de ces commentaires.
            //String userId = climberProfileDTO.getClimberUserId().toString();
            //FileInfo fileInfo = this.filesStorageService.save(file, userId);
//            if (fileInfo != null)
//                climberProfileDTO.setAvatarId(fileInfo.getId());

            ClimberProfileDTO createdClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTO);

            return new ResponseEntity<>(createdClimberProfileDTO, HttpStatus.CREATED);
            //return ResponseEntity.created(new URI("/api/personne-autorises/" + result.getId())).body(result);
        } catch (Exception e) {
            log.error("An error was encountered while retrieving data",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}