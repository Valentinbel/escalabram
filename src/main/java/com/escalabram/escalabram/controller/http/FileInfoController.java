package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileInfoController {

    private static final Logger log = LoggerFactory.getLogger(FileInfoController.class);
    private final FilesStorageService filesStorageService;

    @GetMapping("/avatar/{userId}/id")
    public ResponseEntity<Long> getAvatarId(@PathVariable Long userId) {
        log.info("REST request to get AvatarId (if exists) from userId : {}", userId);
        try {
            Long fileInfoId = filesStorageService.getAvatarId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(fileInfoId);
        } catch (Exception e) {
            log.error("Could not getFile from userId: {}", userId ,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<Resource> getFile(@PathVariable Long userId) {
        log.info("REST request to get File from userId: {}", userId);
        try {
            Resource avatar =  this.filesStorageService.loadAvatarResource(userId);
            String contentType = this.filesStorageService.getContentType(avatar, userId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(avatar);
        } catch (Exception e) {
            log.error("Could not getFile from userId: {}", userId ,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @PostMapping("/avatar/")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userIdString) {
        log.info("REST request to save avatar of userId: {}", userIdString);
        try {
            FileInfo fileInfo = this.filesStorageService.saveAvatar(file, userIdString);
            return ResponseEntity.status(HttpStatus.OK).body(fileInfo.getId());
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename();
            log.error(message,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
