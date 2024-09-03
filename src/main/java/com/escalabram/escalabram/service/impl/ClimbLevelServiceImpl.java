package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.repository.ClimbLevelRepository;
import com.escalabram.escalabram.service.ClimbLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class ClimbLevelServiceImpl implements ClimbLevelService {

    private final ClimbLevelRepository climbLevelRepository;

    public ClimbLevelServiceImpl(ClimbLevelRepository climbLevelRepository) {
        this.climbLevelRepository = climbLevelRepository;
    }

    @Override
    public Set<ClimbLevel> findCimbLevelsByIds(Set<ClimbLevel> climbLevels) {
        Set<ClimbLevel> foundClimbLevels = new HashSet<>();
        climbLevels.forEach(climbLevel -> {
            Optional<ClimbLevel> optClimbLevel = climbLevelRepository.findById(climbLevel.getId());
            optClimbLevel.ifPresent(foundClimbLevels::add);
        });
        return foundClimbLevels;
    }

    @Override
    public Set<ClimbLevel> findBySearches(Search search){
        return climbLevelRepository.findBySearches(search);
    }

    @Override
    public Set<ClimbLevel> getClimbLevelsBySearchId(Long id) {
        return climbLevelRepository.getClimbLevelsBySearchesId(id);
    }
}