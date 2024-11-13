package com.escalabram.escalabram.service;

import com.escalabram.escalabram.service.dto.ClimberProfileDTO;

import java.util.Optional;

public interface ClimberProfileService {

    Optional<ClimberProfileDTO> findByClimberUserId(Long climberUserId);

    boolean existsById(Long climberProfileId);

    ClimberProfileDTO createClimberProfile(ClimberProfileDTO climberProfileDTO);
}
