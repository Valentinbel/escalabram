package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.FileInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
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
    private String userIdString;
    private FileInfo fileInfo;
    private ClimberUser user;
    private final MultipartFile mockFile = mock(MultipartFile.class);

    @BeforeEach
    void setup() {
        userId = 123L;
        userIdString = userId.toString();
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

    @Test
    void saveAvatar_SaveError_CatchIllegalArgumentException() {
        when(climberUSerService.findById(anyLong())).thenReturn(Optional.of(user));
        when(fileInfoService.save(any(FileInfo.class))).thenThrow(new IllegalStateException("Error thrown trying to save FileInfo: " + null));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                filesStorageService.saveAvatar(mockFile, userIdString));

        verify(climberUSerService).findById(anyLong());
        verify(fileInfoService).save(any(FileInfo.class));
        assertEquals("Error thrown trying to save FileInfo: " + null, exception.getMessage());
    }

    @Test
    void saveAvatar_FileUserId_SaveOk() {
        when(climberUSerService.findById(userId)).thenReturn(Optional.of(user));
        when(fileInfoService.save(any(FileInfo.class))).thenReturn(fileInfo);

        FileInfo result = filesStorageService.saveAvatar(mockFile, userIdString);

        verify(climberUSerService).findById(userId);
        verify(fileInfoService).save(any(FileInfo.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(fileInfo, result)
        );
    }

    @Test
    void getContentType_PngFile_PngString(@TempDir Path tempDir) throws IOException {
        Path pngFile = tempDir.resolve("avatar.png");
        Files.write(pngFile, new byte[]{0x00, 0x01}); // Contenu fictif

        when(mockResource.getURI()).thenReturn(pngFile.toUri());

        String contentType = filesStorageService.getContentType(mockResource);

        verify(mockResource).getURI();
        assertTrue(contentType.equals("image/png") || contentType.contains("png"));
    }

    @Test
    void getContentType_UnknownExtension_OctetStreamString(@TempDir Path tempDir) throws IOException {
        Path unknownFile = tempDir.resolve("document.xyz");
        Files.write(unknownFile, new byte[]{0x00, 0x01});
        when(mockResource.getURI()).thenReturn(unknownFile.toUri());

        String contentType = filesStorageService.getContentType(mockResource);

        assertEquals("application/octet-stream", contentType);
    }

    @Test
    void GetContentType_URINotAccessible_ThrowsIOException() throws IOException {
        when(mockResource.getURI()).thenThrow(new IOException("URI not accessible"));

        assertThrows(IOException.class, () -> filesStorageService.getContentType(mockResource));
    }
}
