package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClimberProfileServiceImplTest {

    @InjectMocks
    private ClimberProfileServiceImpl climberProfileService;
    @Mock
    private ClimberUSerService climberUSerService;
    @Mock
    private ClimberProfileRepository climberProfileRepository;
    @Mock
    private ClimberProfileMapper climberProfileMapper;

    private ClimberUser climberUser;
    private ClimberProfile climberProfile;
    private ClimberProfileDTO climberProfileDTO;

    @BeforeEach
    void setupData() {
        climberUser = ClimberUser.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();

        climberProfile =  ClimberProfile.builder()
                .genderId(1L)
                .languageId(2L)
                .climberUser(climberUser)
                .isNotified(true)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .build();

        climberProfileDTO = ClimberProfileDTO.builder()
                .userName("Just Adam")
                .genderId(1L)
                .languageId(2L)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .climberUserId(climberProfile.getClimberUser().getId())
                .build();
    }

    @Test
    void findByClimberUserId_UserId_ThenFound() {
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.of(climberProfile));
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository).findByClimberUserId(climberUser.getId());
        verify(climberProfileMapper).toClimberProfileDTO(climberProfile);
        assertAll(
                () -> assertTrue(optionalClimberProfileDTO.isPresent()),
                () -> assertEquals(optionalClimberProfileDTO, Optional.of(climberProfileDTO))
        );
    }

    @Test
    void findByClimberUserId_WrongUserId_ThenEmpty() {
        climberProfileDTO.setClimberUserId(666L);
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.empty());

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository).findByClimberUserId(climberUser.getId());
        verify(climberProfileMapper, never()).toClimberProfileDTO(climberProfile);
        assertTrue(optionalClimberProfileDTO.isEmpty());
    }

    @Test
    void existsById_climberProfileId_true() {
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(true);

        boolean isTrue = climberProfileService.existsById(climberProfile.getId());

        verify(climberProfileRepository).existsById(climberProfile.getId());
        assertTrue(isTrue);
    }

    @Test
    void existsById_wrongClimberProfileId_false() {
        climberProfileDTO.setClimberUserId(666L);
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(false);

        boolean isFalse = climberProfileService.existsById(climberProfile.getId());

        verify(climberProfileRepository).existsById(climberProfile.getId());
        assertFalse(isFalse);
    }

    @Test
    void saveClimberProfile_insert() {
        climberProfileDTO.setClimberUserId(null);
        when(climberProfileMapper.toClimberProfile(climberProfileDTO)).thenReturn(climberProfile);
        when(climberProfileRepository.save(climberProfile)).thenReturn(climberProfile);
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTO);

        verify(climberUSerService, never()).updateUserNameById(anyLong(), anyString());
        verify(climberProfileMapper).toClimberProfile(climberProfileDTO);
        verify(climberProfileRepository).save(climberProfile);
        verify(climberProfileMapper).toClimberProfileDTO(climberProfile);
        assertEquals(insertedClimberProfileDTO, climberProfileDTO);
    }

    @Test
    void saveClimberProfile_update() {
        when(climberUSerService.updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName())).thenReturn(1);
        when(climberProfileMapper.toClimberProfile(climberProfileDTO)).thenReturn(climberProfile);
        when(climberProfileRepository.save(climberProfile)).thenReturn(climberProfile);
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTO);

        verify(climberUSerService).updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName());
        verify(climberProfileMapper).toClimberProfile(climberProfileDTO);
        verify(climberProfileRepository).save(climberProfile);
        verify(climberProfileMapper).toClimberProfileDTO(climberProfile);
        assertEquals(insertedClimberProfileDTO, climberProfileDTO);
    }

    @Test
    void saveClimberProfile_CatchOnUpdateUserName_Catch() {
        when(climberUSerService.updateUserNameById(anyLong(), anyString()))
                .thenThrow(new IllegalStateException("Error thrown trying to update username for userId: " + climberProfileDTO.getClimberUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberProfileService.saveClimberProfile(climberProfileDTO));

        verify(climberUSerService).updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName());
        verify(climberProfileMapper, never()).toClimberProfile(climberProfileDTO);
        verify(climberProfileRepository, never()).save(climberProfile);
        verify(climberProfileMapper, never()).toClimberProfileDTO(climberProfile);
        assertEquals("Error thrown trying to update username for userId: " + climberProfileDTO.getClimberUserId(), exception.getMessage());
    }

    @Test
    void saveClimberProfile_CatchOnSaveProfile_Catch() {
        when(climberUSerService.updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName())).thenReturn(1);
        when(climberProfileMapper.toClimberProfile(climberProfileDTO)).thenReturn(climberProfile);
        when(climberProfileRepository.save(climberProfile))
                .thenThrow(new IllegalStateException("Error thrown trying to save profile with userId: " + climberProfileDTO.getClimberUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberProfileService.saveClimberProfile(climberProfileDTO));

        verify(climberUSerService).updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName());
        verify(climberProfileMapper).toClimberProfile(climberProfileDTO);
        verify(climberProfileRepository).save(climberProfile);
        verify(climberProfileMapper, never()).toClimberProfileDTO(climberProfile);
        assertEquals("Error thrown trying to save profile with userId: " + climberProfileDTO.getClimberUserId(), exception.getMessage());
    }
}
