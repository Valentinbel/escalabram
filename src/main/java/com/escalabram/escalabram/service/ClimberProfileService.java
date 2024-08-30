package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimberProfile;

import java.util.List;
import java.util.Optional;

public interface ClimberProfileService {

    List<ClimberProfile> findAll();

    Optional<ClimberProfile> findById(Long climberProfileId);

    boolean existsById(Long climberProfileId);

    ClimberProfile createClimberProfile(ClimberProfile climberProfile);

    ClimberProfile save(ClimberProfile climberProfile);
}
