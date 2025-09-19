package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    FileInfo save(MultipartFile file, Long userId);

    //TODO no usages
    Resource load(String fileName, String profileId);
}
