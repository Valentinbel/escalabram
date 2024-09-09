package com.escalabram.escalabram.configuration;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomOIDCUserService extends OidcUserService {

    private final ClimberUserRepository climberUserRepository;

    public CustomOIDCUserService(ClimberUserRepository climberUserRepository) {
        this.climberUserRepository = climberUserRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();
        ClimberUser climberUser = createClimberUserIfNotExists(oidcUser, email);

        String username = climberUser.getUserName();
        return new CustomOidcUser(oidcUser, email, username, climberUser.getRole());
    }

    public ClimberUser createClimberUserIfNotExists(OidcUser oidcUser, String email) {
        return climberUserRepository.findByEmail(email)
                .orElseGet(() -> {
                    ClimberUser newUser = new ClimberUser();
                    newUser.setUserName(oidcUser.getFullName());
                    newUser.setEmail(email);
                    newUser.setRole("USER");
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    return climberUserRepository.save(newUser);
                });
    }
}
