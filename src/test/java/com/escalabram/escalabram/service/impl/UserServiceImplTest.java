package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setupData() {
        user = User.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void existsByUserName_Exists_True() {
        when(userRepository.existsByUserName(user.getUserName())).thenReturn(true);
        boolean result = userService.existsByUserName(user.getUserName());
        verify(userRepository).existsByUserName(user.getUserName());
        assertTrue(result);
    }

    @Test
    void existsByUserName_NoExists_False() {
        when(userRepository.existsByUserName(user.getUserName())).thenReturn(false);
        boolean result = userService.existsByUserName(user.getUserName());
        verify(userRepository).existsByUserName(user.getUserName());
        assertFalse(result);
    }

    @Test
    void existsByEmail_Exists_True() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        boolean result = userService.existsByEmail(user.getEmail());
        verify(userRepository).existsByEmail(user.getEmail());
        assertTrue(result);
    }

    @Test
    void existsByEmail_Exists_False() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        boolean result = userService.existsByEmail(user.getEmail());
        verify(userRepository).existsByEmail(user.getEmail());
        assertFalse(result);
    }

    @Test
    void findById_WrongId_Empty() {
        Long wrongId = 987L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<User> optResult = userService.findById(wrongId);
        verify(userRepository).findById(anyLong());
        assertEquals(Optional.empty(), optResult);
    }

    @Test
    void findById_Id_UserOk() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> optResult = userService.findById(user.getId());
        verify(userRepository).findById(anyLong());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(user, optResult.get())
        );
    }

    @Test
    void save_User_SaveOk() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.save(user);

        verify(userRepository).save(any(User.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(user, result)
        );
    }

    @Test
    void save_DataIntegrityViolationException_Catch() {
        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("We have some trouble, dude"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                userService.save(user));

        verify(userRepository).save(any(User.class));
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to save user: {}" +  user, exception.getMessage())
        );
    }

    @Test
    void updateUserNameById_userIdUsername_Success() {
        String newName = "UnDrame Audrap";
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.updateUserNameById(anyLong(), anyString(), any())).thenReturn(1);
        int result = userService.updateUserNameById(user.getId(), newName);

        verify(userRepository).existsById(user.getId());
        verify(userRepository).updateUserNameById(anyLong(), anyString(), any());
        assertEquals( 1, result);
    }

    @Test
    void updateUserNameById_UserNoExists_Catch() {
        String newName = "UneDame Honda";
        when(userRepository.existsById(user.getId())).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                userService.updateUserNameById(user.getId(), newName));

        verify(userRepository).existsById(user.getId());
        verify(userRepository, never()).updateUserNameById(anyLong(), anyString(), any());
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to update username for userId: " + user.getId(), exception.getMessage())
        );
    }
}
