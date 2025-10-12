package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileInfoController {

    private static final Logger log = LoggerFactory.getLogger(FileInfoController.class);
    private final FilesStorageService filesStorageService;

    ////`${this.baseUrl}climber-profiles/avatar`
    @PostMapping("/avatar")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userIdString) {
        log.info("REST request to save avatar of userId: {}", userIdString);
        try {
            FileInfo fileInfo = this.filesStorageService.saveAvatar(file, userIdString);
            return ResponseEntity.status(HttpStatus.OK).body(fileInfo.getId());
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            log.error(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
