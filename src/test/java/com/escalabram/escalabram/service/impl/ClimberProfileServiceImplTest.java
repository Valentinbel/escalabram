package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.service.ClimberProfileService;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClimberProfileServiceImplTest {

    public static ClimberUser climberUser;
    public static Long climberUserId;

    public static ClimberProfile climberProfile;
    public static ClimberProfile climberProfileNoId;
    public static ClimberProfileDTO climberProfileDTO;
    public static ClimberProfileDTO climberProfileDTONoId;

    @Autowired
    ClimberProfileService climberProfileService;

    @MockBean
    ClimberProfileRepository climberProfileRepository;

    @MockBean
    ClimberProfileMapper climberProfileMapper;

    @BeforeAll
    public static void init() {
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
                "AdamOndraProfileName",
                "path/avatar.png",
                1L,
                2L,
                climberUser,
                true,
                "Salut les boys, c'est Adam" );

        climberProfileNoId = climberProfile;
        climberProfileNoId.setId(null);

        climberProfileDTO = new ClimberProfileDTO(
                1L,
                "AdamOndraProfileName",
                "path/avatar.png",
                1L,
                2L,
                true,
                "Salut les boys, c'est Adam",
                1L);

        climberProfileDTONoId = climberProfileDTO;
        climberProfileDTONoId.setId(null);
    }

    @Test
    void testFindByClimberUserId() {
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.of(climberProfile));
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository, times(1)).findByClimberUserId(climberUser.getId());
        assertEquals(optionalClimberProfileDTO, Optional.of(climberProfileDTO));
    }

    @Test
    void testFindByClimberUserId_empty() {
        when(climberProfileRepository.findByClimberUserId(climberUser.getId())).thenReturn(Optional.empty());
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        Optional<ClimberProfileDTO> optionalClimberProfileDTO = climberProfileService.findByClimberUserId(climberUser.getId());

        verify(climberProfileRepository, times(1)).findByClimberUserId(climberUser.getId());
        verify(climberProfileMapper, times(0)).toClimberProfileDTO(climberProfile);
        assertEquals(optionalClimberProfileDTO, Optional.empty());
    }

    @Test
    void testExistsById_true() {
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(true);

        boolean isTrue = climberProfileService.existsById(climberProfile.getId());

        verify(climberProfileRepository, times(1)).existsById(climberProfile.getId());
        assertTrue(isTrue);
    }

    @Test
    void testExistsById_false() {
        when(climberProfileRepository.existsById(climberProfile.getId())).thenReturn(false);

        boolean isFalse = climberProfileService.existsById(climberProfile.getId());

        verify(climberProfileRepository, times(1)).existsById(climberProfile.getId());
        assertFalse(isFalse);
    }

    @Test
    void testSaveClimberProfile_insert() {
        when(climberProfileMapper.toClimberProfile(climberProfileDTONoId)).thenReturn(climberProfileNoId);
        when(climberProfileRepository.save(climberProfileNoId)).thenReturn(climberProfile);
        when(climberProfileMapper.toClimberProfileDTO(climberProfile)).thenReturn(climberProfileDTO);

        ClimberProfileDTO insertedClimberProfileDTO = climberProfileService.saveClimberProfile(climberProfileDTONoId);

        verify(climberProfileRepository, times(1)).save(climberProfileNoId);
        assertEquals(insertedClimberProfileDTO, climberProfileDTO);
    }
}
