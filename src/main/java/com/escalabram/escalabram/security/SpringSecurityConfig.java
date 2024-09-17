package com.escalabram.escalabram.security;

import com.escalabram.escalabram.security.jwt.AuthEntryPointJwt;
import com.escalabram.escalabram.security.jwt.AuthTokenFilter;
import com.escalabram.escalabram.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity //Pas necessaire (?)
@EnableMethodSecurity
@Configuration
public class SpringSecurityConfig {

    //private final CustomUserDetailsService customUserDetailsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    public SpringSecurityConfig(
            //CustomUserDetailsService customUserDetailsService,
                                UserDetailsServiceImpl userDetailsService,
                                AuthEntryPointJwt unauthorizedHandler) {
        //this.customUserDetailsService = customUserDetailsService;
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/searches/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/api/matches/**").permitAll()
                                .requestMatchers("/api/climber-profiles/**").permitAll()
                                .requestMatchers("/api/climber-profile/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/admin").hasAnyRole("ADMIN");
//                    auth.requestMatchers("/user").hasAnyRole("USER");
//                    auth.anyRequest().authenticated();
//                }).formLogin(Customizer.withDefaults()) ///////////////// Formulaire de login est retourné
//                .oauth2Login(Customizer.withDefaults()).build();
//    }
//
//    @Bean // Service à déplacer dans une classe appropriée?
//    public UserDetailsService users() {  ////// EN PROD on stocke ces users en BBDD
//        UserDetails user = User.builder() ///// mettre ClimberUSer pour voir........................................................
//                .username("user")
//                .password(passwordEncoder().encode("user")) // Password Compromised for PROD
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))  // Password Compromised for PROD
//                .roles("USER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
//            throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
//        return authenticationManagerBuilder.build();
//    }
}
