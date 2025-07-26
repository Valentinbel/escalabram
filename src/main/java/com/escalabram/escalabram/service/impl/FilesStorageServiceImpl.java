package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.FileInfo;
import com.escalabram.escalabram.service.FileInfoService;
import com.escalabram.escalabram.service.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final FileInfoService fileInfoService;

    public FilesStorageServiceImpl(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @Override
    public FileInfo save(MultipartFile file, String userId) {
        try {
            //save in Folder
            Path userIdFolder = getUserIdFolder(userId);
            initFolder(userIdFolder);
            // TODO check ici (avant Files.copy) if fileExists
            // mais si le fichier est différent, il faut l'updater. Comment savoir? CRC?
            // voici Comment: https://claude.ai/share/a91c1e4d-3002-4bb2-aa3d-388bd02fe607 Si on fait la version simple,
            // il faut que l'upload se fasse pas depuis le submit
            // mais depuis le OK du image Cropper

            https://claude.ai/share/a91c1e4d-3002-4bb2-aa3d-388bd02fe607
            Files.copy(file.getInputStream(), userIdFolder.resolve(file.getOriginalFilename()));

            // save in DB
            FileInfo fileToSave = new FileInfo();
            fileToSave.setName(file.getOriginalFilename());
            fileToSave.setUrl(userIdFolder.toString());
            return fileInfoService.save(fileToSave);

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException){
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    private Path getUserIdFolder(String userId) {
        return Paths.get("uploads/userId_" + userId);
    }

    private void initFolder(Path userIdFolder) {
        try {
            Files.createDirectories(userIdFolder);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Resource load(String fileName, String userId) {
        try {
            Path userIdFolder = getUserIdFolder(userId);
            Path file = userIdFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
