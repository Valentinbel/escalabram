package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SearchRepository extends JpaRepository<Search, Long> {

    Optional<Set<Search>> findByClimberProfileId(Long climberProfileId);
}
