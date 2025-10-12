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
    private ClimberProfileServiceImpl climberProfileServiceImpl;
    @Mock
    private ClimberUSerService climberUSerService;
    @Mock
    private ClimberProfileRepository climberProfileRepository;
    @Mock
    private ClimberProfileMapper climberProfileMapper;

    private ClimberUser climberUser;
    private ClimberProfile climberProfile;
    private ClimberProfile climberProfileNoId;
    private ClimberProfileDTO climberProfileDTO;
    private ClimberProfileDTO climberProfileDTONoId;

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
                //.id(1L)
                .genderId(1L)
                .languageId(2L)
                .climberUser(climberUser)
                .isNotified(true)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .build();

        climberProfileNoId = climberProfile;
        climberProfileNoId.setId(null);

        climberProfileDTO = ClimberProfileDTO.builder()
                //.id(1L)
                .userName("Just Adam")
                .genderId(1L)
                .languageId(2L)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .climberUserId(climberProfile.getClimberUser().getId())
                .build();

        climberProfileDTONoId = climberProfileDTO;
        climberProfileDTONoId.setId(null);
    }

    @Test
    void findByClimberUserId_userId_optionalDTO() {
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.of(climberProfile));
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileServiceImpl.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository, times(1)).findByClimberUserId(climberUser.getId());
        assertEquals(optionalClimberProfileDTO, Optional.of(climberProfileDTO));
    }

    @Test
    void findByClimberUserId_wrongUserId_optionalEmpty() {
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.empty());

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileServiceImpl.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository, times(1)).findByClimberUserId(climberUser.getId());
        verify(climberProfileMapper, times(0)).toClimberProfileDTO(climberProfile);
        assertEquals(optionalClimberProfileDTO, Optional.empty());
    }

    @Test
    void existsById_climberProfileId_true() {
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(true);

        boolean isTrue = climberProfileServiceImpl.existsById(climberProfile.getId());

        verify(climberProfileRepository, times(1)).existsById(climberProfile.getId());
        assertTrue(isTrue);
    }

    @Test
    void existsById_wrongClimberProfileId_false() {
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(false);

        boolean isFalse = climberProfileServiceImpl.existsById(climberProfile.getId());

        verify(climberProfileRepository, times(1)).existsById(climberProfile.getId());
        assertFalse(isFalse);
    }

    @Test
    void saveClimberProfile_insert() {
        when(climberUSerService.updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName())).thenReturn(1);
        when(climberProfileMapper.toClimberProfile(climberProfileDTONoId)).thenReturn(climberProfileNoId);
        when(climberProfileRepository.save(climberProfileNoId)).thenReturn(climberProfile);
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileServiceImpl.saveClimberProfile(climberProfileDTONoId);

        verify(climberUSerService).updateUserNameById(climberProfileDTO.getClimberUserId(), climberProfileDTO.getUserName());
        verify(climberProfileMapper).toClimberProfile(climberProfileDTONoId);
        verify(climberProfileRepository, times(1)).save(climberProfileNoId);
        verify(climberProfileMapper).toClimberProfileDTO(climberProfile);
        assertEquals(insertedClimberProfileDTO, climberProfileDTO);
    }
}
