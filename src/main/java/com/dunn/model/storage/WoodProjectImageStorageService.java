package com.dunn.model.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class WoodProjectImageStorageService implements IStorageService{

    private Path rootLocation;

    public WoodProjectImageStorageService(@Qualifier("storageServiceProperties") PropertiesFactoryBean storageServiceProperties){
        try {
            this.rootLocation = Paths.get(storageServiceProperties.getObject().getProperty("storage.location.createwoodproject.permanent"));
        } catch (IOException e) {
            throw new StorageException("Storage path could not be set: ", e);
        }
    }

    @Override
    @PostConstruct
    public void init() {
        StorageServiceHelper.initiateDirectory(rootLocation);
    }

    @Override
    public void store(MultipartFile file, Path targetDirectory) {
        StorageServiceHelper.store(file, targetDirectory);
    }

    @Override
    public Stream<Path> loadAll() {
        return StorageServiceHelper.loadAll(rootLocation);
    }

    @Override
    public Path load(String filename) {
        return StorageServiceHelper.load(rootLocation, filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        return StorageServiceHelper.loadAsResource(rootLocation, filename);
    }

    @Override
    public void deleteAll() {
        StorageServiceHelper.deleteAll(rootLocation);
    }

    @Override
    public void deleteDirectory(Path directoryToDelete) {
        StorageServiceHelper.deleteDirectory(directoryToDelete);
    }

    @Override
    public Path getRootLocation() {
        return this.rootLocation;
    }


    public Path generateWoodProjectPath(String username, String projectName) {
        Path tempFolder = rootLocation.resolve(username).resolve(projectName + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")) + "-" + UUID.randomUUID());

        return tempFolder;
    }

    @Override
    public Path storeToUniqueDirectory(MultipartFile file, String directoryPrefix, Path targetDirectory) {

        return StorageServiceHelper.storeToUniqueDirectory(rootLocation, file, directoryPrefix, targetDirectory);
    }

    public boolean copy(Path source, Path target){
        return StorageServiceHelper.copy(source, target);
    }

    @Override
    public void transferToUserProjectDirectory() {

    }
}
