package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService {

    Long getAvatarId(Long userId);

    Resource loadAvatarResource(Long userId);

    FileInfo saveAvatar(MultipartFile file, String userIdString);

    String getContentType(Resource avatar) throws IOException;
}
