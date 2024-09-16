//package com.escalabram.escalabram.configuration;
//
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    /*
//        Pour user GitHub
//     */
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { //OAuth2User
//        final OAuth2User oAuth2User = super.loadUser(userRequest);
//        String getAttributes = oAuth2User.getAttributes().toString();
//        String email = oAuth2User.getAttribute("email");
//        // En l'état, OAuth2 ne permet pas de connaître l'email. Et donc de faire le findByEmail()
//        System.out.println("/////////////////////");
//        System.out.println("getAttributes: " + getAttributes);
//        System.out.println("email: " + oAuth2User.getAttribute("email"));
//
//
//        System.out.println("/////////////////////");
//        //CustomOAuth2User newUser = new CustomOAuth2User();
//
//        return new CustomOAuth2User(oAuth2User,  email, "USERNAAAAAAAAAAAAME","ROOOOOOOOOOLE");
//    }
//}
