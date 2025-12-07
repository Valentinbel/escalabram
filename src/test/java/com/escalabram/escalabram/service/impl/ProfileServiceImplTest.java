package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Profile;
import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.repository.ProfileRepository;
import com.escalabram.escalabram.service.UserService;
import com.escalabram.escalabram.service.dto.ProfileDTO;
import com.escalabram.escalabram.service.mapper.ProfileMapper;
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
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;
    @Mock
    private UserService userService;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ProfileMapper profileMapper;

    private User user;
    private Profile profile;
    private ProfileDTO profileDTO;

    @BeforeEach
    void setupData() {
        user = User.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();

        profile = Profile.builder()
                .genderId(1L)
                .languageId(2L)
                .user(user)
                .isNotified(true)
                .profileDescription("Salut les boys, c'est Adam")
                .build();

        profileDTO = ProfileDTO.builder()
                .userName("Just Adam")
                .genderId(1L)
                .languageId(2L)
                .profileDescription("Salut les boys, c'est Adam")
                .userId(profile.getUser().getId())
                .build();
    }

    @Test
    void findByUserId_UserId_Found() {
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.of(profile));
        when(profileMapper.toProfileDTO(profile)).thenReturn(profileDTO);

        Optional<ProfileDTO> optionalProfileDTO = profileService.findByUserId(user.getId());

        verify(profileRepository).findByUserId(user.getId());
        verify(profileMapper).toProfileDTO(profile);
        assertAll(
                () -> assertTrue(optionalProfileDTO.isPresent()),
                () -> assertEquals(optionalProfileDTO, Optional.of(profileDTO))
        );
    }

    @Test
    void findByUserId_WrongUserId_Empty() {
        profileDTO.setUserId(666L);
        when(profileRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        Optional<ProfileDTO> optionalProfileDTO = profileService.findByUserId(user.getId());

        verify(profileRepository).findByUserId(user.getId());
        verify(profileMapper, never()).toProfileDTO(profile);
        assertTrue(optionalProfileDTO.isEmpty());
    }

    @Test
    void existsById_ProfileId_True() {
        when(profileRepository.existsById(profile.getId())).thenReturn(true);

        boolean isTrue = profileService.existsById(profile.getId());

        verify(profileRepository).existsById(profile.getId());
        assertTrue(isTrue);
    }

    @Test
    void existsById_WrongProfileId_False() {
        profileDTO.setUserId(666L);
        when(profileRepository.existsById(profile.getId())).thenReturn(false);

        boolean isFalse = profileService.existsById(profile.getId());

        verify(profileRepository).existsById(profile.getId());
        assertFalse(isFalse);
    }

    @Test
    void saveProfile_Insert() {
        profileDTO.setUserId(null);
        when(profileMapper.toProfile(profileDTO)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileMapper.toProfileDTO(profile)).thenReturn(profileDTO);

        ProfileDTO insertedProfileDTO = profileService.saveProfile(profileDTO);

        verify(userService, never()).updateUserNameById(anyLong(), anyString());
        verify(profileMapper).toProfile(profileDTO);
        verify(profileRepository).save(profile);
        verify(profileMapper).toProfileDTO(profile);
        assertEquals(insertedProfileDTO, profileDTO);
    }

    @Test
    void saveProfile_Update() {
        when(userService.updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName())).thenReturn(1);
        when(profileMapper.toProfile(profileDTO)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileMapper.toProfileDTO(profile)).thenReturn(profileDTO);

        ProfileDTO insertedProfileDTO = profileService.saveProfile(profileDTO);

        verify(userService).updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName());
        verify(profileMapper).toProfile(profileDTO);
        verify(profileRepository).save(profile);
        verify(profileMapper).toProfileDTO(profile);
        assertEquals(insertedProfileDTO, profileDTO);
    }

    @Test
    void saveProfile_CatchOnUpdateUserName_Catch() {
        when(userService.updateUserNameById(anyLong(), anyString()))
                .thenThrow(new IllegalStateException("Error thrown trying to update username for userId: " + profileDTO.getUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                profileService.saveProfile(profileDTO));

        verify(userService).updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName());
        verify(profileMapper, never()).toProfile(profileDTO);
        verify(profileRepository, never()).save(profile);
        verify(profileMapper, never()).toProfileDTO(profile);
        assertEquals("Error thrown trying to update username for userId: " + profileDTO.getUserId(), exception.getMessage());
    }

    @Test
    void saveProfile_CatchOnSaveProfile_Catch() {
        when(userService.updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName())).thenReturn(1);
        when(profileMapper.toProfile(profileDTO)).thenReturn(profile);
        when(profileRepository.save(profile))
                .thenThrow(new IllegalStateException("Error thrown trying to save profile with userId: " + profileDTO.getUserId()));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                profileService.saveProfile(profileDTO));

        verify(userService).updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName());
        verify(profileMapper).toProfile(profileDTO);
        verify(profileRepository).save(profile);
        verify(profileMapper, never()).toProfileDTO(profile);
        assertEquals("Error thrown trying to save profile with userId: " + profileDTO.getUserId(), exception.getMessage());
    }
}
