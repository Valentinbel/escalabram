package com.escalabram.escalabram.service;

import com.escalabram.escalabram.service.dto.ProfileDTO;

import java.util.Optional;

public interface ProfileService {

    Optional<ProfileDTO> findByUserId(Long userId);

    boolean existsById(Long profileId);

    ProfileDTO saveProfile(ProfileDTO profileDTO);
}
