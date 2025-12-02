package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.repository.FileInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileInfoServiceImplTest {

    @InjectMocks
    private FileInfoServiceImpl fileInfoService;
    @Mock
    private FileInfoRepository fileInfoRepository;

    private ClimberUser user;
    private FileInfo fileInfo;

    @BeforeEach
    void setupData() {
        user = ClimberUser.builder()
                .id(1984L)
                .userName("AdamOndra")
                .email("adam@ondra.com")
                .password("Password_123")
                .createdAt(LocalDateTime.now())
                .build();

        fileInfo = FileInfo.builder()
                .name("myPic.jpg")
                .url("myrepo/user1/myPic.jpg")
                .climberUser(user)
                .build();
    }

    @Test
    void findByUserId_WrongUserId_ThenEmpty() {
        Long wrongId = 987L;
        when(fileInfoRepository.findByclimberUserId(anyLong())).thenReturn(Optional.empty());

        Optional<FileInfo> optResult =  fileInfoService.findByUserId(wrongId);

        verify(fileInfoRepository).findByclimberUserId(anyLong());
        assertEquals(Optional.empty(), optResult);
    }

    @Test
    void findByUserId_userId_ThenFound() {
        when(fileInfoRepository.findByclimberUserId(anyLong())).thenReturn(Optional.of(fileInfo));

        Optional<FileInfo> optResult =  fileInfoService.findByUserId(user.getId());

        verify(fileInfoRepository).findByclimberUserId(anyLong());
        assertAll(
                () -> assertTrue(optResult.isPresent()),
                () -> assertEquals(fileInfo, optResult.get())
        );
    }

    @Test
    void save_FileInfo_SaveOk() {
        when(fileInfoRepository.save(any(FileInfo.class))).thenReturn(fileInfo);

        FileInfo result = fileInfoService.save(fileInfo);

        verify(fileInfoRepository).save(fileInfo);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(fileInfo, result)
        );
    }

    @Test
    void save_DataIntegrityViolationException_Catch() {
        when(fileInfoRepository.save(any(FileInfo.class)))
                .thenThrow(new DataIntegrityViolationException("We have some trouble, dude"));

        IllegalStateException exception = assertThrows(IllegalStateException.class,() ->
                fileInfoService.save(fileInfo));

        verify(fileInfoRepository).save(any(FileInfo.class));
        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Error thrown trying to save fileInfo: {}" +  fileInfo, exception.getMessage())
        );
    }

    @Test
    void deleteByUrl_Url_Ok() {
        fileInfoService.deleteByUrl(fileInfo.getUrl());
        verify(fileInfoRepository).deleteByUrl(fileInfo.getUrl());
    }
}
