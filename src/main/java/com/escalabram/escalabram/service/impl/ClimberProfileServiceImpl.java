package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClimberProfileServiceImpl implements ClimberProfileService {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileServiceImpl.class);
    private final ClimberProfileRepository climberProfileRepository;
    private final ClimberProfileMapper climberProfileMapper;

    public ClimberProfileServiceImpl(ClimberProfileRepository climberProfileRepository, ClimberProfileMapper climberProfileMapper) {
        this.climberProfileRepository = climberProfileRepository;
        this.climberProfileMapper = climberProfileMapper;
    }

    @Override
    public Optional<ClimberProfileDTO> findByClimberUserId(Long climberUserId) {
        log.debug("ClimberProfile findByClimberUserId : {}", climberUserId);
        Optional<ClimberProfile> optionalClimberProfile = climberProfileRepository.findByClimberUserId(climberUserId);
        if(optionalClimberProfile.isPresent()) {
            ClimberProfileDTO climberProfileDTO = this.climberProfileMapper.toClimberProfileDTO(optionalClimberProfile.get());
            return Optional.ofNullable(climberProfileDTO);
        } else return  Optional.empty();


    }

    @Override
    public boolean existsById(Long climberProfileId) {
        return climberProfileRepository.existsById(climberProfileId);
    }

    @Override
    public ClimberProfileDTO createClimberProfile(ClimberProfileDTO climberProfileDTO) {
        log.debug("climberProfileRequestDTO : {}", climberProfileDTO);
        return save(climberProfileDTO);
    }

    private ClimberProfileDTO save(ClimberProfileDTO climberProfileDTO) {
        ClimberProfile climberProfile = this.climberProfileMapper.toClimberProfile(climberProfileDTO);
        ClimberProfile savedClimberProfile = climberProfileRepository.save(climberProfile);
        return this.climberProfileMapper.toClimberProfileDTO(savedClimberProfile);
    }
}
