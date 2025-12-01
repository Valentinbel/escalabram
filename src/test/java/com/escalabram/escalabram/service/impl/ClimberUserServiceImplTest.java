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

    private ClimberUser climberUser;

    @BeforeEach
    void setupData() {
        climberUser = ClimberUser.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void existsByUserName_Exists_True() {
        when(climberUserRepository.existsByUserName(climberUser.getUserName())).thenReturn(true);
        boolean result = climberUserService.existsByUserName(climberUser.getUserName());
        verify(climberUserRepository).existsByUserName(climberUser.getUserName());
        assertTrue(result);
    }

    @Test
    void existsByUserName_NoExists_False() {
        when(climberUserRepository.existsByUserName(climberUser.getUserName())).thenReturn(false);
        boolean result = climberUserService.existsByUserName(climberUser.getUserName());
        verify(climberUserRepository).existsByUserName(climberUser.getUserName());
        assertFalse(result);
    }

    @Test
    void existsByEmail_Exists_True() {
        when(climberUserRepository.existsByEmail(climberUser.getEmail())).thenReturn(true);
        boolean result = climberUserService.existsByEmail(climberUser.getEmail());
        verify(climberUserRepository).existsByEmail(climberUser.getEmail());
        assertTrue(result);
    }

    @Test
    void existsByEmail_Exists_False() {
        when(climberUserRepository.existsByEmail(climberUser.getEmail())).thenReturn(false);
        boolean result = climberUserService.existsByEmail(climberUser.getEmail());
        verify(climberUserRepository).existsByEmail(climberUser.getEmail());
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
        when(climberUserRepository.findById(climberUser.getId())).thenReturn(Optional.of(climberUser));
        Optional<ClimberUser> optResult = climberUserService.findById(climberUser.getId());
        verify(climberUserRepository).findById(anyLong());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(climberUser, optResult.get())
        );
    }

    @Test
    void save_User_SaveOk() {
        when(climberUserRepository.save(any(ClimberUser.class))).thenReturn(climberUser);

        ClimberUser result = climberUserService.save(climberUser);

        verify(climberUserRepository).save(any(ClimberUser.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(climberUser, result)
        );
    }

    @Test
    void save_DataIntegrityViolationException_Catch() {
        when(climberUserRepository.save(any(ClimberUser.class)))
                .thenThrow(new DataIntegrityViolationException("We have some trouble, dude"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberUserService.save(climberUser));

        verify(climberUserRepository).save(any(ClimberUser.class));
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to save user: {}" +  climberUser, exception.getMessage())
        );
    }

    @Test
    void updateUserNameById_userIdUsername_Success() {
        String newName = "UnDrame Audrap";
        when(climberUserRepository.existsById(climberUser.getId())).thenReturn(true);
        when(climberUserRepository.updateUserNameById(anyLong(), anyString(), any())).thenReturn(1);
        int result = climberUserService.updateUserNameById(climberUser.getId(), newName);

        verify(climberUserRepository).existsById(climberUser.getId());
        verify(climberUserRepository).updateUserNameById(anyLong(), anyString(), any());
        assertEquals( 1, result);
    }

    @Test
    void updateUserNameById_UserNoExists_Catch() {
        String newName = "UneDame Honda";
        when(climberUserRepository.existsById(climberUser.getId())).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberUserService.updateUserNameById(climberUser.getId(), newName));

        verify(climberUserRepository).existsById(climberUser.getId());
        verify(climberUserRepository, never()).updateUserNameById(anyLong(), anyString(), any());
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to update username for userId: " + climberUser.getId(), exception.getMessage())
        );
    }
}
