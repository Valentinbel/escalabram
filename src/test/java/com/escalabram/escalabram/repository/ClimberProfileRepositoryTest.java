package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
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

        ClimberUser relatedUser = new ClimberUser("ChrisSharma", "cricri2@hotmail.com", "password", LocalDateTime.now(), LocalDateTime.now());
        relatedUser.setId(1L);
        relatedUser.setRoles(roleSet);
        entityManager.merge(relatedUser);

        ClimberProfile profileToFind = new ClimberProfile(1L, "Chris_S", "path/myFace.png", 1L, 2L, relatedUser, true, "Blah blah, my life...");
        entityManager.merge(profileToFind);
        Optional<ClimberProfile> optprofileToFind = Optional.of(profileToFind);

        Optional<ClimberProfile> retrievedClimberProfile = climberProfileRepository.findByClimberUserId(1L);

        assertAll(
                () -> assertEquals(retrievedClimberProfile.get().getId(), optprofileToFind.get().getId()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getPassword(), optprofileToFind.get().getClimberUser().getPassword()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getCreatedAt(), optprofileToFind.get().getClimberUser().getCreatedAt()),
                () -> assertEquals(retrievedClimberProfile.get().getClimberUser().getRoles(), optprofileToFind.get().getClimberUser().getRoles())
        );
    }

    @Test
    void findByClimberUserId_whenFindById_thenEmpty() {
        Optional<ClimberProfile> retrievedClimberProfile = climberProfileRepository.findByClimberUserId(99L);
        assertEquals(retrievedClimberProfile, Optional.empty());
    }
}
