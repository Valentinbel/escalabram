package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import com.escalabram.escalabram.service.ClimberUSerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Transactional
@Validated
//@Valid
public class ClimberUserServiceImpl implements ClimberUSerService {

    private final ClimberUserRepository climberUserRepository;

    public ClimberUserServiceImpl(ClimberUserRepository climberUserRepository) {
        this.climberUserRepository = climberUserRepository;
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return climberUserRepository.existsByUserName(userName);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return climberUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<ClimberUser> findById(Long id) {
        return climberUserRepository.findById(id);
    }

    @Override
    public ClimberUser save(ClimberUser user) {
        return climberUserRepository.save(user);
    }
}
