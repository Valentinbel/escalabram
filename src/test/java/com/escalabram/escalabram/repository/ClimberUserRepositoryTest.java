package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.escalabram.escalabram.model.enumeration.EnumRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ClimberUserRepositoryTest {

    @Autowired
    private ClimberUserRepository climberUserRepository;
    @Autowired
    private TestEntityManager entityManager;

    private ClimberUser climberUser;
    private String wrongEmail;
    private String wrongUserName;

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

        wrongEmail = "anotherEmail@wrong.com";
        wrongUserName = "Michel";
    }

    @Test
    void findByEmail_Email_thenFound() {
        Optional<ClimberUser> optResult = climberUserRepository.findByEmail(climberUser.getEmail());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(climberUser, optResult.get())
        );
    }

    @Test
    void findByEmail_Email_thenEmpty() {
        Optional<ClimberUser> optResult = climberUserRepository.findByEmail(wrongEmail);
        assertTrue(optResult.isEmpty());
    }

    @Test
    void existsByUserName_True() {
        boolean result = climberUserRepository.existsByUserName(climberUser.getUserName());
        assertTrue(result);
    }

    @Test
    void existsByUserName_False() {
        boolean result = climberUserRepository.existsByUserName(wrongUserName);
        assertFalse(result);
    }

    @Test
    void existsByEmail_True() {
        boolean result = climberUserRepository.existsByEmail(climberUser.getEmail());
        assertTrue(result);
    }

    @Test
    void existsByEmail_False() {
        boolean result = climberUserRepository.existsByEmail(wrongEmail);
        assertFalse(result);
    }

    @Test
    void updateUserNameById_userIdUserName_updateOk() {
        String newUserName = "Shawn";
        int result = climberUserRepository.updateUserNameById(climberUser.getId(), newUserName, LocalDateTime.now());
        entityManager.clear();
        Optional<ClimberUser> optModifiedUser = climberUserRepository.findByEmail(climberUser.getEmail());
        assertAll(
                () -> assertTrue(optModifiedUser.isPresent()),
                () -> assertEquals(1, result),
                () -> assertEquals(newUserName, optModifiedUser.get().getUserName())
        );
    }

    @Test
    void updateUserNameById_WronfUserId_UpdateChangedNothing() {
        int result = climberUserRepository.updateUserNameById(climberUser.getId(),climberUser.getUserName(), LocalDateTime.now());
        entityManager.clear();
        Optional<ClimberUser> optNotModifiedUser = climberUserRepository.findByEmail(climberUser.getEmail());
        assertAll(
                () -> assertTrue(optNotModifiedUser.isPresent()),
                () -> assertEquals(1, result),
                () -> assertEquals(optNotModifiedUser.get().getUserName(), climberUser.getUserName())
        );
    }
}
