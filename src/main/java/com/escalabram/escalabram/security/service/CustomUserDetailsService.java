//package com.escalabram.escalabram.configuration;
//
//import com.escalabram.escalabram.model.User;
//import com.escalabram.escalabram.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    // Me semble mieux que UserDetailsServiceImpl de BezKoder
//
//    //A Améliorer. On veut que le message d'erreur en Front soit modifiable.
//    // Pour l'instant le Catch empêche le message d'erreur en Front.
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, InternalAuthenticationServiceException {
//        //try {
//            User user = userRepository.findByUserName(userName)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with userName: " + userName));;
//            return new User(user.getUserName(), user.getPassword(), getGrantedAuthorities(user.getRoles().toString())); ///// tostring ??
//
////        } catch (UsernameNotFoundException e) {
////            // Ne renvoie pas le message voulu.
////            log.error("C'est le log ERROOOR: UserName or Password is incorrecto");
////            throw new UsernameNotFoundException("UserName or Password is incorrecto");
////        }
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(String role) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        return authorities;
//    }
//}
