package com.escalabram.escalabram.security.service;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClimberUserRepository climberUserRepository;

    public UserDetailsServiceImpl(ClimberUserRepository climberUserRepository) {
        this.climberUserRepository = climberUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ClimberUser climberUser = climberUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("ClimberUser not found with email: " + email));

        return UserDetailsImpl.build(climberUser);
    }
}
