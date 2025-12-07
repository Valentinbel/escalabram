//package com.escalabram.escalabram.configuration;
//
//import com.escalabram.escalabram.model.User;
//import com.escalabram.escalabram.repository.UserRepository;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class CustomOIDCUserService extends OidcUserService {
////
////    private final UserRepository userRepository;
////
////    public CustomOIDCUserService(UserRepository userRepository) {
////        this.userRepository = userRepository;
////    }
////
////    @Override
////    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
////        final OidcUser oidcUser = super.loadUser(userRequest);
////        String email = oidcUser.getEmail();
////        User user = createUserIfNotExists(oidcUser, email);
////
////        String username = user.getUserName();
////        return new CustomOidcUser(oidcUser, email, username, user.getRole());
////    }
////
////    public User createUserIfNotExists(OidcUser oidcUser, String email) {
////        return userRepository.findByEmail(email)
////                .orElseGet(() -> {
////                    User newUser = new User();
////                    newUser.setUserName(oidcUser.getFullName());
////                    newUser.setEmail(email);
////                    newUser.setRole("USER");
////                    newUser.setCreatedAt(LocalDateTime.now());
////                    newUser.setUpdatedAt(LocalDateTime.now());
////                    return userRepository.save(newUser);
////                });
////    }
//}
