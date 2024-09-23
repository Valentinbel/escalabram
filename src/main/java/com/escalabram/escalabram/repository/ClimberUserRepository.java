package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClimberUserRepository extends JpaRepository<ClimberUser, Long> {

    Optional<ClimberUser> findByUserName(String userName);

    Optional<ClimberUser> findByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}
