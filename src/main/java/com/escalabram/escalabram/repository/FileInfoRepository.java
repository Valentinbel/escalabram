package com.escalabram.escalabram.repository;

import com.escalabram.escalabram.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    void deleteByUrl(String url);

    Optional<FileInfo> findByclimberUserId(@Param("id") Long userId);
}
