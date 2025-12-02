package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberUserRepository;
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
class ClimberUserServiceImplTest {

    @InjectMocks
    private ClimberUserServiceImpl climberUserService;
    @Mock
    private ClimberUserRepository climberUserRepository;

    private ClimberUser user;

    @BeforeEach
    void setupData() {
        user = ClimberUser.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void existsByUserName_Exists_True() {
        when(climberUserRepository.existsByUserName(user.getUserName())).thenReturn(true);
        boolean result = climberUserService.existsByUserName(user.getUserName());
        verify(climberUserRepository).existsByUserName(user.getUserName());
        assertTrue(result);
    }

    @Test
    void existsByUserName_NoExists_False() {
        when(climberUserRepository.existsByUserName(user.getUserName())).thenReturn(false);
        boolean result = climberUserService.existsByUserName(user.getUserName());
        verify(climberUserRepository).existsByUserName(user.getUserName());
        assertFalse(result);
    }

    @Test
    void existsByEmail_Exists_True() {
        when(climberUserRepository.existsByEmail(user.getEmail())).thenReturn(true);
        boolean result = climberUserService.existsByEmail(user.getEmail());
        verify(climberUserRepository).existsByEmail(user.getEmail());
        assertTrue(result);
    }

    @Test
    void existsByEmail_Exists_False() {
        when(climberUserRepository.existsByEmail(user.getEmail())).thenReturn(false);
        boolean result = climberUserService.existsByEmail(user.getEmail());
        verify(climberUserRepository).existsByEmail(user.getEmail());
        assertFalse(result);
    }

    @Test
    void findById_WrongId_Empty() {
        Long wrongId = 987L;
        when(climberUserRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<ClimberUser> optResult = climberUserService.findById(wrongId);
        verify(climberUserRepository).findById(anyLong());
        assertEquals(Optional.empty(), optResult);
    }

    @Test
    void findById_Id_UserOk() {
        when(climberUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<ClimberUser> optResult = climberUserService.findById(user.getId());
        verify(climberUserRepository).findById(anyLong());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(user, optResult.get())
        );
    }

    @Test
    void save_User_SaveOk() {
        when(climberUserRepository.save(any(ClimberUser.class))).thenReturn(user);

        ClimberUser result = climberUserService.save(user);

        verify(climberUserRepository).save(any(ClimberUser.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(user, result)
        );
    }

    @Test
    void save_DataIntegrityViolationException_Catch() {
        when(climberUserRepository.save(any(ClimberUser.class)))
                .thenThrow(new DataIntegrityViolationException("We have some trouble, dude"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberUserService.save(user));

        verify(climberUserRepository).save(any(ClimberUser.class));
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to save user: {}" +  user, exception.getMessage())
        );
    }

    @Test
    void updateUserNameById_userIdUsername_Success() {
        String newName = "UnDrame Audrap";
        when(climberUserRepository.existsById(user.getId())).thenReturn(true);
        when(climberUserRepository.updateUserNameById(anyLong(), anyString(), any())).thenReturn(1);
        int result = climberUserService.updateUserNameById(user.getId(), newName);

        verify(climberUserRepository).existsById(user.getId());
        verify(climberUserRepository).updateUserNameById(anyLong(), anyString(), any());
        assertEquals( 1, result);
    }

    @Test
    void updateUserNameById_UserNoExists_Catch() {
        String newName = "UneDame Honda";
        when(climberUserRepository.existsById(user.getId())).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberUserService.updateUserNameById(user.getId(), newName));

        verify(climberUserRepository).existsById(user.getId());
        verify(climberUserRepository, never()).updateUserNameById(anyLong(), anyString(), any());
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to update username for userId: " + user.getId(), exception.getMessage())
        );
    }
}
