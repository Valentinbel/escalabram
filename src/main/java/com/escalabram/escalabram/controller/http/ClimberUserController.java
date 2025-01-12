package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.service.ClimberUSerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
public class ClimberUserController {

    private static final Logger log = LoggerFactory.getLogger(ClimberUserController.class);
    private final ClimberUSerService climberUSerService;

    public ClimberUserController(ClimberUSerService climberUSerService) {
        this.climberUSerService = climberUSerService;
    }

    @PutMapping("/climber-user/{userId}/{userName}")
    public ResponseEntity<Integer> updateUserNameById(@PathVariable("userId") Long userId, @PathVariable("userName") String userName) {
        try {
            log.debug("REST request to updateUserNameById. userId: {}. userName: {}", userId, userName);
            if(this.climberUSerService.existsById(userId)){
                this.climberUSerService.updateUserNameById(userId, userName);
                // TODO Ca doit générer une erreur si blank, empty, moins de 4 caracteres ou plus de 20
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Une erreur a été rencontrée lors de mise à terme d'une condamnation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}