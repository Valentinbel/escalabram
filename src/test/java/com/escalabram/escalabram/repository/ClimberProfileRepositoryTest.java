package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
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
class ClimberProfileRepositoryTest {

    @Autowired
    private ClimberProfileRepository climberProfileRepository;
    @Autowired
    private TestEntityManager entityManager;

    private ClimberProfile profile;

    @BeforeEach
    void setup() {
        Role userRole = Role.builder()
                .roleName(ROLE_USER).build();
        entityManager.persist(userRole);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        ClimberUser user = ClimberUser.builder()
                .userName("CrisSharma")
                .email("cricri@gmail.com")
                .password("password1234")
                .roles(roleSet)
                .createdAt(LocalDateTime.now())
                .build();
        entityManager.merge(user);

        FileInfo fileInfo = FileInfo.builder()
                .name("selfie")
                .url("./uploads/uderId")
                .climberUser(user)
                .build();
        entityManager.merge(fileInfo);

        profile =ClimberProfile.builder()
                .genderId(1L)
                .languageId(2L)
                .isNotified(true)
                .climberUser(user)
                .climberProfileDescription("Blah blah, my life...")
                .build();
        entityManager.merge(profile);
    }

    @Test
    void findByClimberUserId_Id_thenSuccess() {
        Optional<ClimberProfile> optResult = climberProfileRepository.findByClimberUserId(profile.getClimberUser().getId());

        assertAll(
                () -> assertEquals(profile.getId(), optResult.get().getId()),
                () -> assertEquals(profile.getClimberUser().getPassword(), optResult.get().getClimberUser().getPassword()),
                () -> assertEquals(profile.getClimberUser().getCreatedAt().getMinute(), optResult.get().getClimberUser().getCreatedAt().getMinute()),
                () -> assertEquals(profile.getClimberUser().getRoles(), optResult.get().getClimberUser().getRoles())
        );
    }

    @Test
    void findByClimberUserId_WrongId_thenEmpty() {
        Optional<ClimberProfile> retrievedClimberProfile = climberProfileRepository.findByClimberUserId(99L);
        assertEquals(retrievedClimberProfile, Optional.empty());
    }
}
