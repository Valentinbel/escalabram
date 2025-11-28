package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;

import java.util.Optional;

public interface FileInfoService {

    Optional<FileInfo> findByUserId(Long userId);

    FileInfo save(FileInfo fileInfo);

    void deleteByUrl(String url);
}
