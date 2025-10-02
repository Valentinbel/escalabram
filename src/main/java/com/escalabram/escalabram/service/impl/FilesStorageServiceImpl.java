package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.ClimberUSerService;
import com.escalabram.escalabram.service.FileInfoService;
import com.escalabram.escalabram.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {
    private static final Logger log = LoggerFactory.getLogger(FilesStorageServiceImpl.class);
    private final FileInfoService fileInfoService;
    private final ClimberUSerService climberUSerService;

    @Override
    public FileInfo saveAvatar(MultipartFile file, String userIdString) {
        try {
            Long userId= Long.parseLong(userIdString);
            // TODO: Il faut réduire la taille de l'image aussi. En front ou en back ??
            //delete Folder & save in Folder
            Path userFolder = getUserFolder(userId);
            FileUtils.deleteDirectory(userFolder.toFile());
            initFolder(userFolder);
            if (file.getOriginalFilename() != null)
                Files.copy(file.getInputStream(), userFolder.resolve(file.getOriginalFilename()));

            // delete and save in DB
            FileInfo newFileInfo = deleteAndSaveFileInfo(userId, file);

            //update user with fileInfo
            int updated = climberUSerService.updateFileInfoByUserId(userId, newFileInfo.getId());

            if (updated != 0 )
                return newFileInfo;
            throw new IllegalStateException("We couldn't associate fileInfo to user. FileInfo: " + newFileInfo + "userId: " + userId);
        } catch (FileAlreadyExistsException e) {
            throw new IllegalArgumentException("A file of that name already exists." + e.getMessage());
        } catch (IOException  e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void initFolder(Path userIdFolder) {
        try {
            Files.createDirectories(userIdFolder);
        } catch (IOException e) {
            throw new IllegalStateException("Could not initialize folder for upload!");
        }
    }

    private FileInfo deleteAndSaveFileInfo(Long userId, MultipartFile file) {
        Optional<ClimberUser> optUser =  climberUSerService.findById(userId);
        if (optUser.isPresent()) {
            Path userFolder = getUserFolder(userId);
            fileInfoService.deleteByUrl(userFolder.toString());
            FileInfo fileToSave = FileInfo.builder()
                    .name(file.getOriginalFilename())
                    .url(userFolder.toString())
                    .build();
            try {
                return fileInfoService.save(fileToSave);
            } catch (DataIntegrityViolationException e) {
                log.error("We had a problem to manage this FileInfo: {}", fileToSave);
                throw new IllegalStateException("Error thrown trying to save FileInfo: " + fileToSave);
            }
        } else {
            log.error("user not found with id: {}", userId);
            throw new IllegalArgumentException("user not found with id: {}" + userId);
        }
    }

    @Override
    public Resource load(String fileName, String userIdString) {
        // TODO recupérer aussi info from DB ?
        try {
            Long userId= Long.parseLong(userIdString);
            Path userIdFolder = getUserFolder(userId);
            Path file = userIdFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new IllegalStateException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Error: " + e.getMessage());
        }
    }

    private Path getUserFolder(Long userId) {
        return Paths.get("uploads/userId_" + userId);
    }
}
