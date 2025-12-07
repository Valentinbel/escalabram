package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.User;

import java.util.Optional;

public interface UserService {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    User save(User user);

    int updateUserNameById(Long userId, String userName);
}
