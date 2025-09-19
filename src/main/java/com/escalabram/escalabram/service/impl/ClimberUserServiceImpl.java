package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import com.escalabram.escalabram.service.ClimberUSerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClimberUserServiceImpl implements ClimberUSerService {

    private final ClimberUserRepository climberUserRepository;

    public ClimberUserServiceImpl(ClimberUserRepository climberUserRepository) {
        this.climberUserRepository = climberUserRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return climberUserRepository.existsById(id);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return climberUserRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
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

    @Override
    public int updateUserNameById(Long userId, String userName) {
        return climberUserRepository.updateUserNameById(userId, userName);
    }
}
