package com.escalabram.escalabram.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

    void save(MultipartFile file, String profileId);

    Resource load(String fileName, String profileId);

//    void deleteAll();
//
//    Stream<Path> loadAll();
}
