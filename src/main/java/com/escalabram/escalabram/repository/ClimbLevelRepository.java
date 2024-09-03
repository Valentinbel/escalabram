package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimbLevel;
import com.escalabram.escalabram.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ClimbLevelRepository extends JpaRepository<ClimbLevel, Long> {

    Set<ClimbLevel> findBySearches(Search search);
}
