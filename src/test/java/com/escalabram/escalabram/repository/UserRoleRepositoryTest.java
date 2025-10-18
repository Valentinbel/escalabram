package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findByRoleName_Enum_Success() {
        Role role = Role.builder()
                .roleName(EnumRole.ROLE_USER).build();
        entityManager.persist(role);
        Optional<Role> optRole = userRoleRepository.findByRoleName(EnumRole.ROLE_USER);

        assertAll(
                () -> assertTrue(optRole.isPresent()),
                () -> assertEquals(role, optRole.get())
        );
    }

    @Test
    void findByRoleName_NotOnDBEnum_Empty() {
        Optional<Role> optRole = userRoleRepository.findByRoleName(EnumRole.ROLE_USER);
        assertTrue(optRole.isEmpty());
    }
}
