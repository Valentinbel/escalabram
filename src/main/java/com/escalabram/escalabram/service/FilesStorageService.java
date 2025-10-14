package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService {

    FileInfo saveAvatar(MultipartFile file, String userIdString);

    Resource loadAvatar(Long userId);

    String getContentType(Resource avatar, Long userId) throws IOException;
}
