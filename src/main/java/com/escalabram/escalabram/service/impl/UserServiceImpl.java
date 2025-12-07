package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.repository.UserRepository;
import com.escalabram.escalabram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error thrown trying to save user: {}" +  user, e);
        }
    }

    @Override
    public int updateUserNameById(Long userId, String userName) {
        if(existsById(userId))
            return userRepository.updateUserNameById(userId, userName, LocalDateTime.now());
        else throw new IllegalStateException("Error thrown trying to update username for userId: " + userId);
    }

    private boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
