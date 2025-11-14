package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.security.payload.request.LoginRequest;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.request.TokenRefreshRequest;
import com.escalabram.escalabram.security.payload.response.JwtResponse;
import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.security.payload.response.TokenRefreshResponse;
import com.escalabram.escalabram.service.AuthService;
import com.escalabram.escalabram.service.ClimberUSerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final ClimberUSerService climberUSerService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("REST request to register a user: {}", signUpRequest.getEmail());
        // TODO mettre cette logique dans un Service.
        if (climberUSerService.existsByUserName(signUpRequest.getUserName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: UserName is already taken!: " + signUpRequest.getUserName()));
        if (climberUSerService.existsByEmail(signUpRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        authService.createUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("REST request to log a user: {}", loginRequest.getEmail());
        JwtResponse authenticatedUser = authService.authenticateUser(loginRequest);
        log.info("User logged");
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        log.info("REST request to refresh token");
        TokenRefreshResponse refreshedToken = authService.refreshtoken(request);
        return ResponseEntity.ok(refreshedToken);
    }

    @PostMapping("/signout/{userId}")
    public ResponseEntity<MessageResponse> logoutUser(@PathVariable Long userId) {
        log.info("REST request to logout userId: {}", userId);
        MessageResponse logOutResponse = authService.logoutUser(userId);
        return ResponseEntity.ok(logOutResponse);
    }
}