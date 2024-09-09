package com.escalabram.escalabram.configuration;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final ClimberUserRepository climberUserRepository;

    public CustomUserDetailsService(ClimberUserRepository climberUserRepository) {
        this.climberUserRepository = climberUserRepository;
    }

    //A Améliorer. On veut que le message d'erreur en Front soit modifiable.
    // Pour l'instant le Catch empêche le message d'erreur en Front.
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, InternalAuthenticationServiceException {
        try {
            ClimberUser user = climberUserRepository.findByUserName(userName);
            return new User(user.getUserName(), user.getPassword(), getGrantedAuthorities(user.getRole()));
        } catch (UsernameNotFoundException e) {
            // Ne renvoie pas le message voulu.
            log.error("C'est le log ERROOOR: UserName or Password is incorrecto");
            throw new UsernameNotFoundException("UserName or Password is incorrecto");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
