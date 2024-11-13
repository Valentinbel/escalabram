package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimberUser;

import java.util.Optional;

public interface ClimberUSerService {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<ClimberUser> findById(Long id);

    ClimberUser save(ClimberUser user);
}
