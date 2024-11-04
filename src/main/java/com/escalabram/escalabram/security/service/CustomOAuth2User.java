//package com.escalabram.escalabram.configuration;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public class CustomOAuth2User implements OAuth2User {
//    private final OAuth2User oAuth2User;
//    private final String email;
//    private final String username;
//    private final String role;
//
//    public CustomOAuth2User(OAuth2User oAuth2User, String email, String username, String role) {
//        this.oAuth2User = oAuth2User;
//        this.email = email;
//        this.username = username;
//        this.role = role;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return oAuth2User.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        //authorities.addAll(oAuth2User.getAuthorities()); //Pareil que : oAuth2User.getAuthorities().forEach(auth -> authorities.add(auth));
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return authorities;
//    }
//
//    @Override
//    public String getName() {
//        return oAuth2User.getAttribute("name");
//    }
//
//    //OAuth2 ne récupère pas l'email pour le moment.
//    public String getEmail() {
//        return oAuth2User.getAttribute("email");
//    }
//
//}
