package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClimberProfileRepository extends JpaRepository<ClimberProfile, Long> {

    Optional<ClimberProfile> findByClimberUserId(Long climberUserId);
}
