package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.configuration.UserDetailsImpl;
import com.escalabram.escalabram.configuration.payload.request.LoginRequest;
import com.escalabram.escalabram.configuration.payload.request.SignupRequest;
import com.escalabram.escalabram.configuration.payload.response.JwtResponse;
import com.escalabram.escalabram.configuration.payload.response.MessageResponse;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import com.escalabram.escalabram.repository.UserRoleRepository;
import com.escalabram.escalabram.utils.JwtUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final ClimberUserRepository climberUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          ClimberUserRepository climberUserRepository,
                          UserRoleRepository userRoleRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.climberUserRepository = climberUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .toList();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (climberUserRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!: " + signUpRequest.getUserName()));
        }

        if (climberUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        ClimberUser user = new ClimberUser(signUpRequest.getUserName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), LocalDateTime.now(), LocalDateTime.now());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role role = userRoleRepository.findByRoleName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                if(role.equals("admin")) {
                    Role adminRole = userRoleRepository.findByRoleName(EnumRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: RoleName is not found."));
                    roles.add(adminRole);

                } else {
                    Role userRole = userRoleRepository.findByRoleName(EnumRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        climberUserRepository.save(user);
        log.info("New user created: {}", user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}