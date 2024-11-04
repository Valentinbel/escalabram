package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClimbLevelRepository extends JpaRepository<ClimbLevel, Long> {

    Set<ClimbLevel> findBySearches(Search search);

    Set<ClimbLevel> getClimbLevelsBySearchesId(Long id);
}
