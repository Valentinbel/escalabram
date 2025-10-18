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
        Role roleUser = Role.builder()
                .roleName(ROLE_USER).build();
        entityManager.persist(roleUser);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleUser);

        ClimberUser relatedUser = ClimberUser.builder()
                .userName("CrisSharma")
                .email("cricri@gmail.com")
                .password("password1234")
                .createdAt(LocalDateTime.now())
                .build();
        relatedUser.setId(1L);
        relatedUser.setRoles(roleSet);
        entityManager.merge(relatedUser);

        FileInfo fileInfo = FileInfo.builder()
                .id(1L)
                .name("selfie")
                .url("./uploads/uderId")
                .climberUser(relatedUser)
                .build();
        entityManager.merge(fileInfo);

        profile =ClimberProfile.builder()
                .id(1L)
                .genderId(1L)
                .languageId(2L)
                .isNotified(true)
                .climberUser(relatedUser)
                .climberProfileDescription("Blah blah, my life...")
                .build();
        entityManager.merge(profile);
    }

    @Test
    void findByClimberUserId_Id_thenSuccess() {
        Optional<ClimberProfile> optResult = climberProfileRepository.findByClimberUserId(1L);

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
