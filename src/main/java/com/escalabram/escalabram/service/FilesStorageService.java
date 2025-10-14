package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    FileInfo saveAvatar(MultipartFile file, String userIdString);

    Resource loadAvatar(Long userId);
}
