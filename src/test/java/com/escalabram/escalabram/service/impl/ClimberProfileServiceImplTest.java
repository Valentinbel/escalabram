package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
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
public class ClimberProfileServiceImplTest {

    public static FileInfo fileInfo;

    public static ClimberUser climberUser;

    public static ClimberProfile climberProfile;
    public static ClimberProfile climberProfileNoId;
    public static ClimberProfileDTO climberProfileDTO;
    public static ClimberProfileDTO climberProfileDTONoId;

    @InjectMocks
    ClimberProfileServiceImpl climberProfileServiceImpl;

    @Mock
    ClimberProfileRepository climberProfileRepository;

    @Mock
    ClimberProfileMapper climberProfileMapper;

    @BeforeEach
    void setupData() {
        climberUser = ClimberUser.builder()
                .id(1L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();


        fileInfo = FileInfo.builder()
                //.id(1L)
                .name("selfPortrait")
                .url("./uploads/userId_1")
                .climberUser(climberUser)
                .build();

        climberProfile =  ClimberProfile.builder()
                .id(1L)
                .genderId(1L)
                .languageId(2L)
                .climberUser(climberUser)
                .isNotified(true)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .build();

        climberProfileNoId = climberProfile;
        climberProfileNoId.setId(null);

        climberProfileDTO = ClimberProfileDTO.builder()
                .id(1L)
                .avatarId(1L)
                .userName("Just Adam")
                .genderId(1L)
                .languageId(2L)
                .climberProfileDescription("Salut les boys, c'est Adam")
                .climberUserId(1L)
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
        when(climberProfileMapper.toClimberProfile(climberProfileDTONoId)).thenReturn(climberProfileNoId);
        when(climberProfileRepository.save(climberProfileNoId)).thenReturn(climberProfile);
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileServiceImpl.saveClimberProfile(climberProfileDTONoId);

        verify(climberProfileRepository, times(1)).save(climberProfileNoId);
        assertEquals(insertedClimberProfileDTO, climberProfileDTO);
    }
}
