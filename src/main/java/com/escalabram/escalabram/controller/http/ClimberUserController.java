package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.service.ClimberUSerService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@Validated // TODO: find why @PathVariable @Size seems to work without Validated
public class ClimberUserController {

    private static final Logger log = LoggerFactory.getLogger(ClimberUserController.class);
    private final ClimberUSerService climberUSerService;

    @PutMapping("/climber-user/{userId}/{userName}")
    public ResponseEntity<String> updateUserNameById(@PathVariable Long userId,
                                                     @PathVariable @Size(min = 4, max = 20) String userName) {
        try {
            log.info("REST request to updateUserNameById. userId: {}. userName: {}", userId, userName);
            if(this.climberUSerService.existsById(userId)){
                this.climberUSerService.updateUserNameById(userId, userName);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
           // remplacer ResponseEntity<String> par ResponseEntity<MessageResponse> comme dans Auth Controller
        } catch (Exception e) {
            log.error("Une erreur a été rencontrée lors de mise à terme d'une condamnation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}