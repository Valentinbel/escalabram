package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;

import java.util.Set;

public interface ClimbLevelService {

    Set<ClimbLevel> findCimbLevelsByIds(Set<ClimbLevel> climbLevelIds);

    Set<ClimbLevel> findBySearches(Search search);
}
