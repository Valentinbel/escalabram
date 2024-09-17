package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.configuration.payload.request.LoginRequest;
import com.escalabram.escalabram.configuration.payload.request.SignupRequest;
import com.escalabram.escalabram.configuration.payload.response.JwtResponse;
import com.escalabram.escalabram.configuration.payload.response.MessageResponse;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.service.AuthService;
import com.escalabram.escalabram.service.ClimberUSerService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final ClimberUSerService climberUSerService;
    private final AuthService authService;

    public AuthController(ClimberUSerService climberUSerService, AuthService authService) {
        this.climberUSerService = climberUSerService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse  authenticatedUser = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (climberUSerService.existsByUserName(signUpRequest.getUserName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!: " + signUpRequest.getUserName()));

        if (climberUSerService.existsByEmail(signUpRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

        ClimberUser newUser =   authService.createUser(signUpRequest);
        log.info("New user created: {}", newUser); // TODO DO not sent in PROD
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}