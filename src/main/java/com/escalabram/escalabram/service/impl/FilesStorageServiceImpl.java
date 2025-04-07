package com.escalabram.escalabram.service.impl;

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

    private final String parentFolder= "uploads/";

    @Override
    public void save(MultipartFile file, String profileId) {
        try {
            Path profileIdFolder = getprofileIdFolder(profileId);
            init(profileIdFolder);
            Files.copy(file.getInputStream(), profileIdFolder.resolve(file.getOriginalFilename()));

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException){
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    private Path getprofileIdFolder(String profileId) {
        return Paths.get(parentFolder + profileId);
    }

    private void init(Path profileIdFolder) {
        try {
            Files.createDirectories(profileIdFolder );
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Resource load(String fileName, String profileId) {
        try {
            Path profileIdFolder = getprofileIdFolder(profileId);
            Path file = profileIdFolder.resolve(fileName);
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

//    @Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(root.toFile());
//    }
//
//    @Override
//    public Stream<Path> loadAll() {
//        try {
//            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not load the files!");
//        }
//    }
}
