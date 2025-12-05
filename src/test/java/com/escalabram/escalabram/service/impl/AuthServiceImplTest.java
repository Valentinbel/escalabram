package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.security.service.RefreshTokenService;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    UserRoleService userRoleService;
    @Mock
    ClimberUSerService climberUSerService;
    @Mock
    RefreshTokenService refreshTokenService;

    private Role userRole;
    private SignupRequest signupRequest;
    private ClimberUser user;
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @BeforeEach
    void setupData() {
        userRole = new Role(1L, EnumRole.ROLE_USER);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        String password = "password123";

        signupRequest = new SignupRequest();
        signupRequest.setUserName("janja");
        signupRequest.setEmail("janja@g.com");
        signupRequest.setPassword(password);

        String encodedPassword = encoder.encode(signupRequest.getPassword());
        user = ClimberUser.builder()
                .userName(signupRequest.getUserName())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .roles(userRoles)
                .createdAt(localDateTime)
                .build();
    }

    @Test
    void createUser_RoleNotFound_RuntimeException() {
        when(userRoleService.findByRoleName(EnumRole.ROLE_USER))
                .thenThrow(new RuntimeException("Error: Role is not found."));

        RuntimeException exception = assertThrows(RuntimeException.class,() ->
                authService.createUser(signupRequest));

        verify(climberUSerService, never()).save(any(ClimberUser.class));
        assertEquals("Error: Role is not found.", exception.getMessage());
    }

    @Test
    void createUser_Role_User() {
        when(userRoleService.findByRoleName(EnumRole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(climberUSerService.save(any(ClimberUser.class))).thenReturn(user);

        ClimberUser result = authService.createUser(signupRequest);

        verify(userRoleService).findByRoleName((EnumRole.ROLE_USER));
        verify(climberUSerService).save(any(ClimberUser.class));
        assertEquals(user, result);
    }

        // TODO authenticateUser() & refreshtoken()

    @Test
    void logoutUser_NotFoundUser_Trow() {
        when(climberUSerService.findById(user.getId())).thenThrow(new NoSuchElementException());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,() ->
                authService.logoutUser(user.getId()));
        verify(climberUSerService).findById(user.getId());
        assertNull(exception.getMessage());
    }

    @Test
    void logoutUser_UserId_LogOut() {
        when(climberUSerService.findById(user.getId())).thenReturn(Optional.of(user));
        when(refreshTokenService.deleteByUserId(user.getId())).thenReturn(1);

        MessageResponse result = authService.logoutUser(user.getId());
        verify(climberUSerService).findById(user.getId());
        verify(refreshTokenService).deleteByUserId(user.getId());
        assertEquals("Log out successful!", result.getMessage());
    }
}
