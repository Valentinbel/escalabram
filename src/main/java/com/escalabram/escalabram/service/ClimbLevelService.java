package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimbLevel;

import java.util.Set;

public interface ClimbLevelService {

    Set<ClimbLevel> retrieveCimbLevelsFromIds(Set<ClimbLevel> climbLevelIds);
}