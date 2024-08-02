package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.ClimberProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClimberProfileServiceImpl implements ClimberProfileService {

    private static final Logger log = LoggerFactory.getLogger(ClimberProfileServiceImpl.class);
    private final ClimberProfileRepository climberProfileRepository;

    public ClimberProfileServiceImpl(ClimberProfileRepository climberProfileRepository) {
        this.climberProfileRepository = climberProfileRepository;
    }


    @Override
    public List<ClimberProfile> findAll() {
        log.debug("findAll ClimberProfile");
        return climberProfileRepository.findAll();
    }

    @Override
    public Optional<ClimberProfile> findById(Long climberProfileId) {
        log.debug("ClimberProfile findById. climberProfileId : {}", climberProfileId);
        return climberProfileRepository.findById(climberProfileId);
    }

    @Override
    public void createClimberProfile(ClimberProfile climberProfile) {
        log.debug("createClimberProfile : {}", climberProfile);
        save(climberProfile);
    }

    @Override
    public ClimberProfile save(ClimberProfile climberProfile) {
        return climberProfileRepository.save(climberProfile);
    }
}
