package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import org.junit.jupiter.api.BeforeAll;
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
    public static Long fileInfoId;

    public static ClimberUser climberUser;
    public static Long climberUserId;

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

    @BeforeAll
    static void init() {
        fileInfo = new FileInfo(
                "selfPortrait",
                "./upload/1"
        );
        fileInfoId = 1L;
        fileInfo.setId(fileInfoId);

        climberUser = new ClimberUser(
                "AdamOndraUserName",
                "adam@ondra.com",
                "Password_123",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        climberUserId = 1L;
        climberUser.setId(climberUserId);

        climberProfile = new ClimberProfile(
                1L,
                fileInfo,
                1L,
                2L,
                climberUser,
                true,
                "Salut les boys, c'est Adam" );

        climberProfileNoId = climberProfile;
        climberProfileNoId.setId(null);

        climberProfileDTO = new ClimberProfileDTO(
                1L,
                fileInfo,
                1L,
                2L,
                true,
                "Salut les boys, c'est Adam",
                1L);

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
