package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimberUser;

import java.util.Optional;

public interface ClimberUSerService {

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    Optional<ClimberUser> findById(Long id);

    ClimberUser save(ClimberUser user);

}
