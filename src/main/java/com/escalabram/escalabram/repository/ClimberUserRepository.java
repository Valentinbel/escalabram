package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClimberUserRepository extends JpaRepository<ClimberUser, Long> {

    Optional<ClimberUser> findByUserName(String userName);

    Optional<ClimberUser> findByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}
