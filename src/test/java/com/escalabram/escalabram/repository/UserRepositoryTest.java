package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.User;
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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private String wrongEmail;
    private String wrongUserName;

    @BeforeEach
    void setup() {
        Role roleUser = Role.builder()
                .roleName(ROLE_USER).build();
        entityManager.persist(roleUser);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleUser);

        user = User.builder()
                .userName("Brooke")
                .email("brooketta@mail.it")
                .password("tartineOrclimber")
                .roles(roleSet)
                .createdAt(LocalDateTime.MIN)
                .build();
        entityManager.persist(user);

        wrongEmail = "anotherEmail@wrong.com";
        wrongUserName = "Michel";
    }

    @Test
    void findByEmail_Email_thenFound() {
        Optional<User> optResult = userRepository.findByEmail(user.getEmail());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(user, optResult.get())
        );
    }

    @Test
    void findByEmail_Email_thenEmpty() {
        Optional<User> optResult = userRepository.findByEmail(wrongEmail);
        assertTrue(optResult.isEmpty());
    }

    @Test
    void existsByUserName_True() {
        boolean result = userRepository.existsByUserName(user.getUserName());
        assertTrue(result);
    }

    @Test
    void existsByUserName_False() {
        boolean result = userRepository.existsByUserName(wrongUserName);
        assertFalse(result);
    }

    @Test
    void existsByEmail_True() {
        boolean result = userRepository.existsByEmail(user.getEmail());
        assertTrue(result);
    }

    @Test
    void existsByEmail_False() {
        boolean result = userRepository.existsByEmail(wrongEmail);
        assertFalse(result);
    }

    @Test
    void updateUserNameById_userIdUserName_updateOk() {
        String newUserName = "Shawn";
        int result = userRepository.updateUserNameById(user.getId(), newUserName, LocalDateTime.now());
        entityManager.clear();
        Optional<User> optModifiedUser = userRepository.findByEmail(user.getEmail());
        assertAll(
                () -> assertTrue(optModifiedUser.isPresent()),
                () -> assertEquals(1, result),
                () -> assertEquals(newUserName, optModifiedUser.get().getUserName())
        );
    }

    @Test
    void updateUserNameById_WronfUserId_UpdateChangedNothing() {
        int result = userRepository.updateUserNameById(user.getId(),user.getUserName(), LocalDateTime.now());
        entityManager.clear();
        Optional<User> optNotModifiedUser = userRepository.findByEmail(user.getEmail());
        assertAll(
                () -> assertTrue(optNotModifiedUser.isPresent()),
                () -> assertEquals(1, result),
                () -> assertEquals(optNotModifiedUser.get().getUserName(), user.getUserName())
        );
    }
}
