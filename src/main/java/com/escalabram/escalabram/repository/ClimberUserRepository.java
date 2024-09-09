package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClimberUserRepository extends JpaRepository<ClimberUser, Long> {
    ClimberUser findByUserName(String userName);

    Optional<ClimberUser> findByEmail(String email);
}
