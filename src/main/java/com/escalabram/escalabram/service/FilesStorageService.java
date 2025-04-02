package com.escalabram.escalabram.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file);

    Resource load(String fileName);

//    void deleteAll();
//
//    Stream<Path> loadAll();
}
