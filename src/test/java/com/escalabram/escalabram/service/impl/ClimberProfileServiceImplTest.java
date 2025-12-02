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

    private ClimberUser user;
    private ClimberProfile profile;
    private ClimberProfileDTO profileDTO;

    @BeforeEach
    void setupData() {
        user = ClimberUser.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();

        profile = ClimberProfile.builder()
                .genderId(1L)
                .languageId(2L)
                .climberUser(user)
                .isNotified(true)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .build();

        profileDTO = ClimberProfileDTO.builder()
                .userName("Just Adam")
                .genderId(1L)
                .languageId(2L)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .climberUserId(profile.getClimberUser().getId())
                .build();
    }

    @Test
    void findByClimberUserId_UserId_ThenFound() {
        when(climberProfileRepository.findByClimberUserId(user.getId())).thenReturn(Optional.of(profile));
        when(climberProfileMapper.toClimberProfileDTO(profile)).thenReturn(profileDTO);

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(user.getId());

        verify(climberProfileRepository).findByClimberUserId(user.getId());
        verify(climberProfileMapper).toClimberProfileDTO(profile);
        assertAll(
                () -> assertTrue(optionalClimberProfileDTO.isPresent()),
                () -> assertEquals(optionalClimberProfileDTO, Optional.of(profileDTO))
        );
    }

    @Test
    void findByClimberUserId_WrongUserId_ThenEmpty() {
        profileDTO.setClimberUserId(666L);
        when(climberProfileRepository.findByClimberUserId(user.getId())).thenReturn(Optional.empty());

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(user.getId());

        verify(climberProfileRepository).findByClimberUserId(user.getId());
        verify(climberProfileMapper, never()).toClimberProfileDTO(profile);
        assertTrue(optionalClimberProfileDTO.isEmpty());
    }

    @Test
    void existsById_climberProfileId_true() {
        when(climberProfileRepository.existsById(profile.getId())).thenReturn(true);

        boolean isTrue = climberProfileService.existsById(profile.getId());

        verify(climberProfileRepository).existsById(profile.getId());
        assertTrue(isTrue);
    }

    @Test
    void existsById_wrongClimberProfileId_false() {
        profileDTO.setClimberUserId(666L);
        when(climberProfileRepository.existsById(profile.getId())).thenReturn(false);

        boolean isFalse = climberProfileService.existsById(profile.getId());

        verify(climberProfileRepository).existsById(profile.getId());
        assertFalse(isFalse);
    }

    @Test
    void saveClimberProfile_insert() {
        profileDTO.setClimberUserId(null);
        when(climberProfileMapper.toClimberProfile(profileDTO)).thenReturn(profile);
        when(climberProfileRepository.save(profile)).thenReturn(profile);
        when(climberProfileMapper.toClimberProfileDTO(profile)).thenReturn(profileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileService.saveClimberProfile(profileDTO);

        verify(climberUSerService, never()).updateUserNameById(anyLong(), anyString());
        verify(climberProfileMapper).toClimberProfile(profileDTO);
        verify(climberProfileRepository).save(profile);
        verify(climberProfileMapper).toClimberProfileDTO(profile);
        assertEquals(insertedClimberProfileDTO, profileDTO);
    }

    @Test
    void saveClimberProfile_update() {
        when(climberUSerService.updateUserNameById(profileDTO.getClimberUserId(), profileDTO.getUserName())).thenReturn(1);
        when(climberProfileMapper.toClimberProfile(profileDTO)).thenReturn(profile);
        when(climberProfileRepository.save(profile)).thenReturn(profile);
        when(climberProfileMapper.toClimberProfileDTO(profile)).thenReturn(profileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileService.saveClimberProfile(profileDTO);

        verify(climberUSerService).updateUserNameById(profileDTO.getClimberUserId(), profileDTO.getUserName());
        verify(climberProfileMapper).toClimberProfile(profileDTO);
        verify(climberProfileRepository).save(profile);
        verify(climberProfileMapper).toClimberProfileDTO(profile);
        assertEquals(insertedClimberProfileDTO, profileDTO);
    }

    @Test
    void saveClimberProfile_CatchOnUpdateUserName_Catch() {
        when(climberUSerService.updateUserNameById(anyLong(), anyString()))
                .thenThrow(new IllegalStateException("Error thrown trying to update username for userId: " + profileDTO.getClimberUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberProfileService.saveClimberProfile(profileDTO));

        verify(climberUSerService).updateUserNameById(profileDTO.getClimberUserId(), profileDTO.getUserName());
        verify(climberProfileMapper, never()).toClimberProfile(profileDTO);
        verify(climberProfileRepository, never()).save(profile);
        verify(climberProfileMapper, never()).toClimberProfileDTO(profile);
        assertEquals("Error thrown trying to update username for userId: " + profileDTO.getClimberUserId(), exception.getMessage());
    }

    @Test
    void saveClimberProfile_CatchOnSaveProfile_Catch() {
        when(climberUSerService.updateUserNameById(profileDTO.getClimberUserId(), profileDTO.getUserName())).thenReturn(1);
        when(climberProfileMapper.toClimberProfile(profileDTO)).thenReturn(profile);
        when(climberProfileRepository.save(profile))
                .thenThrow(new IllegalStateException("Error thrown trying to save profile with userId: " + profileDTO.getClimberUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                climberProfileService.saveClimberProfile(profileDTO));

        verify(climberUSerService).updateUserNameById(profileDTO.getClimberUserId(), profileDTO.getUserName());
        verify(climberProfileMapper).toClimberProfile(profileDTO);
        verify(climberProfileRepository).save(profile);
        verify(climberProfileMapper, never()).toClimberProfileDTO(profile);
        assertEquals("Error thrown trying to save profile with userId: " + profileDTO.getClimberUserId(), exception.getMessage());
    }
}
