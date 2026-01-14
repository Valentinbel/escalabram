package com.escalabram.escalabram.controller.http;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class LoginController {
    //private static final Logger log = LoggerFactory.getLogger(LoginController.class);
//    private final OAuth2AuthorizedClientService authorizedClientService;
//
//    @GetMapping("/user")
//    public String getUser() {
//        return "Welcome, User";
//    }
//
//    @GetMapping("/admin")
//    public String getAdmin() {
//        return "Welcome, Admin";
//    }
//
//    @GetMapping("/login")
//    public String getUserInfo(Principal user, @AuthenticationPrincipal OidcUser oidcUser) {
//
//        StringBuffer userInfo = new StringBuffer();
//        if(user instanceof UsernamePasswordAuthenticationToken) {
//            userInfo.append(getUserNamePasswordLoginInfo(user));
//        } else if (user instanceof OAuth2AuthenticationToken) {
//            userInfo.append(getOAuth2LoginInfo(user, oidcUser));
//        }
//        return userInfo.toString();
//    }
//
//    private StringBuffer getUserNamePasswordLoginInfo (Principal user) {
//        StringBuffer userNameInfo = new StringBuffer();
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
//        if(token.isAuthenticated()) {
//            User u = (User) token.getPrincipal(); // (User) à renplacer par User ?
//            userNameInfo.append("Welcome, " + u.getUsername() + ". Tu t'es connecté avec user et password.  <br><br>"); // là aussi
//            userNameInfo.append("u.getAuthorities() : " + u.getAuthorities()); // là aussi
//        } else {
//            userNameInfo.append("Not Authorised, Bro");
//        }
//        return userNameInfo;
//    }
//
//    private StringBuffer getOAuth2LoginInfo(Principal user, OidcUser oidcUser) {
//        StringBuffer protectedInfo = new StringBuffer();
//
//        OAuth2AuthenticationToken authToken  = (OAuth2AuthenticationToken) user;
//        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(
//                authToken.getAuthorizedClientRegistrationId(), authToken.getName());
//
//        if (authToken.isAuthenticated()){
//            Map<String, Object> userAttributes = (authToken.getPrincipal()).getAttributes();
//
//            String userToken = authClient.getAccessToken().getTokenValue(); //Ne pas montrer ça en vrai sur notre vrai site.
//            protectedInfo.append("Welcome, " + userAttributes.get("name") + "<br><br>");
//            protectedInfo.append("email: " + userAttributes.get("email") + "<br><br>"); // Apparait comme Null car donnée protégée
//            protectedInfo.append("authentication method: " + authClient.getClientRegistration().getRegistrationId() + "<br><br>");
//            protectedInfo.append("Access Token : " + userToken + "<br><br>"); //Ne pas montrer ça en vrai sur notre vrai site.
//
//            if(oidcUser != null) { // Google
//                OidcIdToken idToken = oidcUser.getIdToken();
//                if (idToken != null) { //On ne montrera pas tout ça....
//                    protectedInfo.append("idToken value: " + idToken.getTokenValue() + "<br><br>");
//                    protectedInfo.append("Token mapped values: " + idToken.getTokenValue() + "<br>");
//                    Map<String, Object> claims = idToken.getClaims();
//                    for (String key : claims.keySet()) { //iterate over entrySet??? pour voir
//                        protectedInfo.append("<strong>* " + key + " </strong>: " + claims.get(key) + "<br>");
//                    }
//                }
//            }
//        }
//        else {
//            protectedInfo.append("Not Authorised");
//        }
//        return protectedInfo;
//    }


//}
