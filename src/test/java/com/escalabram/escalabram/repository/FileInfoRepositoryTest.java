package com.escalabram.escalabram.repository;

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
class FileInfoRepositoryTest {

    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private TestEntityManager entityManager;

    private ClimberUser climberUser;
    private FileInfo fileInfo;

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

        fileInfo = FileInfo.builder()
                .name("myPic.jpg")
                .url("myrepo/user1/myPic.jpg")
                .climberUser(climberUser)
                .build();
        entityManager.persist(fileInfo);
    }

    @Test
    void deleteByUrl_Ok() {
        Optional<FileInfo> existsBefore = fileInfoRepository.findByclimberUserId(climberUser.getId());
        fileInfoRepository.deleteByUrl(fileInfo.getUrl());

        assertAll(
                () -> assertTrue(existsBefore.isPresent()),
                () -> assertEquals(existsBefore.get().getUrl(), fileInfo.getUrl()),
                () -> assertNull(entityManager.find(fileInfo.getClass(), fileInfo.getId()))
        );
    }

    @Test
    void findByclimberUserId_Id_ThenFound() {
        Optional<FileInfo> optResult = fileInfoRepository.findByclimberUserId(climberUser.getId());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(fileInfo, optResult.get())
        );
    }

    @Test
    void findByclimberUserId_Id_ThenEmpty() {
        Long wrongUser = climberUser.getId() + 1;
        Optional<FileInfo> optResult = fileInfoRepository.findByclimberUserId(wrongUser);
        assertTrue(optResult.isEmpty());
    }
}
