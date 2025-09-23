package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    void deleteByUrl(String url);
}
