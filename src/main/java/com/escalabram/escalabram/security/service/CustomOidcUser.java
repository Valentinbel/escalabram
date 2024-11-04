//package com.escalabram.escalabram.configuration;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.oidc.OidcIdToken;
//import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public class CustomOidcUser implements OidcUser{
//    private final OidcUser oidcUser;
//    private final String email;
//    private final String username;
//    private final String role;
//
//    public CustomOidcUser(OidcUser oidcUser, String email, String username, String role) {
//        this.oidcUser = oidcUser;
//        this.email = email;
//        this.username = username;
//        this.role = role;
//    }
//
//    @Override
//    public Map<String, Object> getClaims() {
//        return oidcUser.getClaims();
//    }
//
//    @Override
//    public OidcUserInfo getUserInfo() {
//        return oidcUser.getUserInfo();
//    }
//
//    @Override
//    public OidcIdToken getIdToken() {
//        return oidcUser.getIdToken();
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return oidcUser.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return authorities;
//    }
//
//    @Override
//    public String getName() {
//        System.out.println("*******************oidcUser.getName() :" + oidcUser.getName());
//        return oidcUser.getName();
//    }
//}
