package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    FileInfo saveAvatar(MultipartFile file, String userIdString);

    //TODO no usages
    Resource load(String fileName, String userIdString);
}
