package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.FileInfo;

public interface FileInfoService {
    void deleteByUrl(String url);

    FileInfo save(FileInfo fileInfo);
}
