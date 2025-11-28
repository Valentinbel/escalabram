package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClimberProfileServiceImpl implements ClimberProfileService {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileServiceImpl.class);
    private final ClimberUSerService climberUSerService;
    private final ClimberProfileRepository climberProfileRepository;
    private final ClimberProfileMapper climberProfileMapper;

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
    public ClimberProfileDTO saveClimberProfile(ClimberProfileDTO climberProfileDTO) {
        log.debug("climberProfileRequestDTO : {}", climberProfileDTO);
        try {
            if (climberProfileDTO.getClimberUserId() != null &&  climberProfileDTO.getUserName() != null)
                climberUSerService.updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName());

            ClimberProfile climberProfile = this.climberProfileMapper.toClimberProfile(climberProfileDTO);

            ClimberProfile savedClimberProfile = climberProfileRepository.save(climberProfile);
            return this.climberProfileMapper.toClimberProfileDTO(savedClimberProfile);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error thrown trying to save profile with userId: " + climberProfileDTO.getClimberUserId());
        }
    }
}
