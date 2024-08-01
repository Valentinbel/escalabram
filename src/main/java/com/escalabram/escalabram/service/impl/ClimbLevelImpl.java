package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.repository.ClimbLevelRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class ClimbLevelImpl implements ClimbLevelService {

    private final ClimbLevelRepository climbLevelRepository;

    public ClimbLevelImpl(ClimbLevelRepository climbLevelRepository) {
        this.climbLevelRepository = climbLevelRepository;
    }

    @Override
    public Set<ClimbLevel> retrieveCimbLevelsFromIds(Set<ClimbLevel> climbLevelIds) {
        Set<ClimbLevel> newClimbLevels = new HashSet<>();
        climbLevelIds.forEach(eachClimbLevel -> {
            Optional<ClimbLevel> climbLevel = climbLevelRepository.findById(eachClimbLevel.getId());
            climbLevel.ifPresent(newClimbLevels::add);
        });
        return newClimbLevels;
    }
}