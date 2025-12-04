package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.FileInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilesStorageServiceImplTest {

    @InjectMocks
    @Spy
    private FilesStorageServiceImpl filesStorageService;
    @Mock
    private FileInfoService fileInfoService;
    @Mock
    private ClimberUSerService climberUSerService;

    private Long userId;
    private FileInfo fileInfo;

    @BeforeEach
    void setup() {
        userId = 123L;
        ClimberUser user = ClimberUser.builder()
                .id(userId)
                .userName("MejdiSchalck")
                .email("Mejdi@s.com")
                .password("xyz")
                .createdAt(LocalDateTime.MIN)
                .build();

        fileInfo = FileInfo.builder()
                .id(22L)
                .name("selfie")
                .url("src/test/resources/avatarTest.png")
                .climberUser(user)
                .build();
    }
    @Test
    void getAvatarId_DBEmpty_Null() {
        Long wrongId = 987L;
        when(fileInfoService.findByUserId(wrongId)).thenReturn(Optional.empty());

        Long result = filesStorageService.getAvatarId(wrongId);

        verify(fileInfoService).findByUserId(wrongId);
        assertNull(result);
    }

    @Test
    void getAvatarId_IllegalStateException_Catch() {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                filesStorageService.getAvatarId(userId));

        verify(fileInfoService).findByUserId(userId);
        assertEquals("Could not read the file!", exception.getMessage());
    }

    @Test
    void getAvatarId_userId_AvatarId() {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));

        Long result = filesStorageService.getAvatarId(userId);
        verify(fileInfoService).findByUserId(userId);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(fileInfo.getId(), result)
        );
    }




}
