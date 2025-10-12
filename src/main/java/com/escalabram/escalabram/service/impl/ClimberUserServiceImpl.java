package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import com.escalabram.escalabram.service.ClimberUSerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClimberUserServiceImpl implements ClimberUSerService {

    private final ClimberUserRepository climberUserRepository;

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
        if(existsById(userId))
            return climberUserRepository.updateUserNameById(userId, userName);
        else throw new IllegalStateException("Error thrown trying to update username for userId: " + userId);
    }
}
