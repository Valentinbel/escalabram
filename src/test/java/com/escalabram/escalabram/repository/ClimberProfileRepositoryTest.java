package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClimberProfileRepositoryTest {

    @Autowired
    private ClimberProfileRepository climberProfileRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByClimberUserId_whenFindById_thenSuccess() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(entityManager.find(Role.class, 1L));
        entityManager.persist(entityManager.find(Role.class, 1L));

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
                .build();
        entityManager.merge(fileInfo);

        ClimberProfile profileToFind =ClimberProfile.builder()
                .id(1L)
                .genderId(1L)
                .languageId(2L)
                .isNotified(true)
                .climberUser(relatedUser)
                .climberProfileDescription("Blah blah, my life...")
                .build();
        entityManager.merge(profileToFind);
        Optional<ClimberProfile> optprofileToFind = Optional.of(profileToFind);

        Optional<ClimberProfile> retrievedClimberProfile = climberProfileRepository.findByClimberUserId(1L);

        assertAll(
                () -> assertEquals(retrievedClimberProfile.get().getId(), optprofileToFind.get().getId()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getPassword(), optprofileToFind.get().getClimberUser().getPassword()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getCreatedAt().getMinute(), optprofileToFind.get().getClimberUser().getCreatedAt().getMinute()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getRoles(), optprofileToFind.get().getClimberUser().getRoles())
        );
    }

    @Test
    void findByClimberUserId_whenFindById_thenEmpty() {
        Optional<ClimberProfile> retrievedClimberProfile = climberProfileRepository.findByClimberUserId(99L);
        assertEquals(retrievedClimberProfile, Optional.empty());
    }
}
