package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.repository.FileInfoRepository;
import com.escalabram.escalabram.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    @Override
    public Optional<FileInfo> findByUserId(Long userId) {
        return fileInfoRepository.findByclimberUserId(userId);
    }

    @Override
    public FileInfo save(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }

    @Override
    public void deleteByUrl(String url) {
        fileInfoRepository.deleteByUrl(url);
    }
}
