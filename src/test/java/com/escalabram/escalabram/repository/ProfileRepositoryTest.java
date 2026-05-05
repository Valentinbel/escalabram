package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Profile;
import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.escalabram.escalabram.model.enumeration.EnumRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Profile profile;

    @BeforeEach
    void setup() {
        Role userRole = Role.builder()
                .roleName(ROLE_USER).build();
        entityManager.persist(userRole);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        User user = User.builder()
                .userName("CrisSharma")
                .email("cricri@gmail.com")
                .password("password1234")
                .roles(roleSet)
                .createdAt(LocalDateTime.now())
                .build();
        entityManager.persist(user);

        FileInfo fileInfo = FileInfo.builder()
                .name("selfie")
                .url("./uploads/uderId")
                .user(user)
                .build();
        entityManager.persist(fileInfo);

        profile = Profile.builder()
                .genderId(1L)
                .isNotified(true)
                .user(user)
                .profileDescription("Blah blah, my life...")
                .build();
        entityManager.persist(profile);
    }

    @Test
    void findByUserId_Id_Success() {
        Optional<Profile> optResult = profileRepository.findByUserId(profile.getUser().getId());
        System.out.println("profile.getId() " + profile.getId());
        System.out.println("optResult.get().getId() " + optResult.get().getId());
        assertAll(
                () -> assertEquals(profile.getId(), optResult.get().getId()),
                () -> assertEquals(profile.getUser().getPassword(), optResult.get().getUser().getPassword()),
                () -> assertEquals(profile.getUser().getCreatedAt().getMinute(), optResult.get().getUser().getCreatedAt().getMinute()),
                () -> assertEquals(profile.getUser().getRoles(), optResult.get().getUser().getRoles())
        );
    }

    @Test
    void findByUserId_WrongId_Empty() {
        Optional<Profile> optResult = profileRepository.findByUserId(99L);
        assertEquals(optResult, Optional.empty());
    }
}
