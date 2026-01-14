package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    @Mock
    private UserRoleRepository userRoleRepository;

    private Role role;

    @BeforeEach
    void setupData() {
        role = new Role(1L, EnumRole.ROLE_USER);
    }

    @Test
    void findByRoleName_WrongInput_Empty() {
        when(userRoleRepository.findByRoleName(any())).thenReturn(Optional.empty());
        Optional<Role> optResult = userRoleService.findByRoleName(EnumRole.ROLE_USER);
        verify(userRoleRepository).findByRoleName(any());
        assertEquals(Optional.empty(), optResult);
    }

    @Test
    void findByRoleName_roleName_FoundOK() {
        when(userRoleRepository.findByRoleName(EnumRole.ROLE_USER)).thenReturn(Optional.of(role));
        Optional<Role> optResult = userRoleService.findByRoleName(EnumRole.ROLE_USER);
        verify(userRoleRepository).findByRoleName(any());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(role, optResult.get())
        );
    }
}
