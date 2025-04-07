package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.repository.FileInfoRepository;
import com.escalabram.escalabram.service.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {

    private static final Logger log = LoggerFactory.getLogger(FileInfoServiceImpl.class);
    private final FileInfoRepository fileInfoRepository;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }


    @Override
    public FileInfo save() {
        return null;
    }
}
