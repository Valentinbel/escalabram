package com.escalabram.escalabram.security.service;

import com.escalabram.escalabram.exception.TokenRefreshException;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.RefreshToken;
import com.escalabram.escalabram.repository.RefreshTokenRepository;
import com.escalabram.escalabram.service.ClimberUSerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService { ///////////////////// Mettre une implémentation???????????????????????????????

    @Value("${pinya.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClimberUSerService climberUSerService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, ClimberUSerService climberUSerService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.climberUSerService = climberUSerService;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByClimberUserId(Long userId) {
        return refreshTokenRepository.findByClimberUserId(userId);
    }

    public RefreshToken createRefreshToken(Long userId) {

        RefreshToken refreshToken = new RefreshToken();

        ClimberUser climberUser = climberUSerService.findById(userId).orElseThrow();
        refreshToken.setClimberUser(climberUser);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public int deleteByUserId(Long userId) {
        int numoflines = refreshTokenRepository.deleteByClimberUser(climberUSerService.findById(userId).orElseThrow());
        refreshTokenRepository.flush();
        return numoflines;
    }
}