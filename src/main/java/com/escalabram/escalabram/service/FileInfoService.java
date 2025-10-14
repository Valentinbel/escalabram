package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;

import java.util.Optional;

public interface FileInfoService {
    void deleteByUrl(String url);

    FileInfo save(FileInfo fileInfo);

    Optional<FileInfo> findByUderId(Long userId);
}
