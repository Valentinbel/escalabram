package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByClimberUserId(Long climberUserId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    int deleteByClimberUser(ClimberUser climberUser);
}
