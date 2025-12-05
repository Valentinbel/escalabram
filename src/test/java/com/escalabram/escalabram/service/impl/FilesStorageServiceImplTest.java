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
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
    @Mock
    private Resource mockResource;

    private Long userId;
    private FileInfo fileInfo;
    private ClimberUser user;
    private final MultipartFile mockFile = mock(MultipartFile.class);

    @BeforeEach
    void setup() {
        userId = 123L;
        user = ClimberUser.builder()
                .id(userId)
                .userName("MejdiSchalck")
                .email("Mejdi@s.com")
                .password("xyz")
                .createdAt(LocalDateTime.MIN)
                .build();

        fileInfo = FileInfo.builder()
                .id(22L)
                .name("selfie")
                .url("fake/url/selfie.png")
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
    void getAvatarId_NotReadableFile_Catch() {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                filesStorageService.getAvatarId(userId));

        verify(fileInfoService).findByUserId(userId);
        assertEquals("Could not read the file!", exception.getMessage());
    }

    @Test
    void getAvatarId_userId_AvatarId() throws MalformedURLException {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));
        doReturn(mockResource).when(filesStorageService).createResource(any(Path.class));
        when(mockResource.isReadable()).thenReturn(true);

        Long result = filesStorageService.getAvatarId(userId);

        verify(fileInfoService).findByUserId(userId);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(fileInfo.getId(), result)
        );
    }

    @Test
    void loadAvatarResource_Empty_CatchIllegalStateException() {
        Long wrongId = 987L;
        when(fileInfoService.findByUserId(wrongId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() ->
                filesStorageService.loadAvatarResource(wrongId));

        verify(fileInfoService).findByUserId(wrongId);
        assertEquals("There is no file related to this user: {}" + wrongId, exception.getMessage());
    }

    @Test
    void loadAvatarResource_NotReadableFile_Catch() throws MalformedURLException {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));
        doReturn(mockResource).when(filesStorageService).createResource(any(Path.class));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                filesStorageService.loadAvatarResource(userId));

        verify(fileInfoService).findByUserId(userId);
        assertEquals("Could not read the file!", exception.getMessage());
    }

    @Test
    void loadAvatarResource_userId_Resource() throws MalformedURLException {
        when(fileInfoService.findByUserId(userId)).thenReturn(Optional.of(fileInfo));
        doReturn(mockResource).when(filesStorageService).createResource(any(Path.class));
        when(mockResource.isReadable()).thenReturn(true);

        Resource result = filesStorageService.loadAvatarResource(userId);
        verify(fileInfoService).findByUserId(userId);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(mockResource, result)
        );
    }

    @Test
    void saveAvatar_EmptyUser_CatchIllegalArgumentException() {
        Long wrongId = 987L;
        String wrongUserId = wrongId.toString();
        when(climberUSerService.findById(wrongId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() ->
                filesStorageService.saveAvatar(mockFile, wrongUserId));

        verify(climberUSerService).findById(wrongId);
        assertEquals("user not found with id: {}" + wrongId, exception.getMessage());
    }

}
