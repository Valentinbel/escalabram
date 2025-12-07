package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.model.Role;
import com.escalabram.escalabram.model.enumeration.EnumRole;
import com.escalabram.escalabram.security.payload.request.SignupRequest;
import com.escalabram.escalabram.security.payload.response.MessageResponse;
import com.escalabram.escalabram.security.service.RefreshTokenService;
import com.escalabram.escalabram.service.UserService;
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
    UserService userService;
    @Mock
    RefreshTokenService refreshTokenService;

    private Role userRole;
    private SignupRequest signupRequest;
    private User user;
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
        user = User.builder()
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

        verify(userService, never()).save(any(User.class));
        assertEquals("Error: Role is not found.", exception.getMessage());
    }

    @Test
    void createUser_Role_User() {
        when(userRoleService.findByRoleName(EnumRole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userService.save(any(User.class))).thenReturn(user);

        User result = authService.createUser(signupRequest);

        verify(userRoleService).findByRoleName((EnumRole.ROLE_USER));
        verify(userService).save(any(User.class));
        assertEquals(user, result);
    }

        // TODO authenticateUser() & refreshtoken()

    @Test
    void logoutUser_NotFoundUser_Trow() {
        when(userService.findById(user.getId())).thenThrow(new NoSuchElementException());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,() ->
                authService.logoutUser(user.getId()));
        verify(userService).findById(user.getId());
        assertNull(exception.getMessage());
    }

    @Test
    void logoutUser_UserId_LogOut() {
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        when(refreshTokenService.deleteByUserId(user.getId())).thenReturn(1);

        MessageResponse result = authService.logoutUser(user.getId());
        verify(userService).findById(user.getId());
        verify(refreshTokenService).deleteByUserId(user.getId());
        assertEquals("Log out successful!", result.getMessage());
    }
}
