package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.RefreshToken;
import com.escalabram.escalabram.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.escalabram.escalabram.model.enumeration.EnumRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private TestEntityManager entityManager;

    private RefreshToken refreshToken;
    private String token;
    private ClimberUser climberUser;

    @BeforeEach
    void setup() {
        Role roleUser = Role.builder()
                .roleName(ROLE_USER).build();
        entityManager.persist(roleUser);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleUser);

        climberUser = ClimberUser.builder()
                .userName("Brooke")
                .email("brooketta@mail.it")
                .password("tartineOrclimber")
                .roles(roleSet)
                .createdAt(LocalDateTime.MIN)
                .build();
        entityManager.persist(climberUser);

        token = "isThisTokenOrTolkien?";
        refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(60));
        refreshToken.setClimberUser(climberUser);
        entityManager.persist(refreshToken);
    }

    @Test
    void findByToken_Token_ThenFound() {
        Optional<RefreshToken> optResult = refreshTokenRepository.findByToken(token);
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(refreshToken, optResult.get())
        );
    }

    @Test
    void findByToken_Token_thenEmpty() {
        String wrongToken = "Token.IsThisAMovie?";
        Optional<RefreshToken> optResult = refreshTokenRepository.findByToken(wrongToken);
        assertTrue(optResult.isEmpty());
    }

    @Test
    void findByClimberUserId_UserId_ThenFound() {
        Optional<RefreshToken> optResult = refreshTokenRepository.findByClimberUserId(climberUser.getId());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(refreshToken, optResult.get())
        );
    }

    @Test
    void findByClimberUserId_UserId_thenEmpty() {
        Long wrongUser = climberUser.getId() +1;
        Optional<RefreshToken> optResult = refreshTokenRepository.findByClimberUserId(wrongUser);
        assertTrue(optResult.isEmpty());

    }

    @Test
    void deleteByClimberUser_Ok() {
        Optional<RefreshToken> existsBefore = refreshTokenRepository.findByClimberUserId(climberUser.getId());
        int result = refreshTokenRepository.deleteByClimberUser(climberUser);

        assertAll(
                () -> assertTrue(existsBefore.isPresent()),
                () -> assertEquals(1, result),
                () -> assertEquals(existsBefore.get().getToken(), refreshToken.getToken()),
                () -> assertNull(entityManager.find(refreshToken.getClass(), refreshToken.getId()))
        );
    }
}
