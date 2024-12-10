package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.exception.TokenRefreshException;
import com.escalabram.escalabram.model.RefreshToken;
import com.escalabram.escalabram.security.payload.request.TokenRefreshRequest;
import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.security.payload.response.TokenRefreshResponse;
import com.escalabram.escalabram.security.service.RefreshTokenService;
import com.escalabram.escalabram.security.service.UserDetailsImpl;
import com.escalabram.escalabram.security.payload.request.LoginRequest;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.response.JwtResponse;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.service.AuthService;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.UserRoleService;
import com.escalabram.escalabram.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRoleService userRoleService;
    private final ClimberUSerService climberUSerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(PasswordEncoder encoder, UserRoleService userRoleService, ClimberUSerService climberUSerService,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.encoder = encoder;
        this.userRoleService = userRoleService;
        this.climberUSerService = climberUSerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public ClimberUser createUser(SignupRequest signUpRequest) {
        ClimberUser user = new ClimberUser(signUpRequest.getUserName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), LocalDateTime.now(), LocalDateTime.now());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role role = userRoleService.findByRoleName(EnumRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                if(role.equalsIgnoreCase("admin")) {
                    Role adminRole = userRoleService.findByRoleName(EnumRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: RoleName is not found."));
                    roles.add(adminRole);

                } else {
                    Role userRole = userRoleService.findByRoleName(EnumRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return climberUSerService.save(user);
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .toList();

        Long userId = userDetails.getId();

        Optional<RefreshToken> existingUserToken =  refreshTokenService.findByClimberUserId(userId);
        if (existingUserToken.isPresent() // Token still active, we return the existing one
                && existingUserToken.orElseThrow().getExpiryDate().isAfter(Instant.now())) {
            return new JwtResponse(jwt, existingUserToken.orElseThrow().getToken(), userDetails.getId(),
                    userDetails.getUsername(), userDetails.getEmail(), roles);
        } else {
            refreshTokenService.deleteByUserId(userId);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                    userDetails.getUsername(), userDetails.getEmail(), roles);
        }
    }

    @Override
    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getClimberUser)
                .map(climberUSer -> {
                    String token = jwtUtils.generateTokenFromUserName(climberUSer.getUserName());
                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,"Refresh token is not in database!"));
    }

    @Override
    public MessageResponse logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return new MessageResponse("Log out successful!");
    }
}
