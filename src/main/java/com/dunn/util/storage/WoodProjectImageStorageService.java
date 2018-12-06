package com.dunn.util.storage;

import com.dunn.util.pathgenerators.PathGenerator;
import com.dunn.util.pathgenerators.PathInfoHolder;
import com.dunn.util.pathgenerators.WoodProjectPathGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    private PathGenerator pathGenerator = new WoodProjectPathGenerator();

    public WoodProjectImageStorageService(@Qualifier("storageServiceProperties") PropertiesFactoryBean storageServiceProperties){
        try {
            this.rootLocation = Paths.get(storageServiceProperties.getObject().getProperty("storage.location.createwoodproject.permanent"));
        } catch (IOException e) {
            throw new StorageException("Storage uipaths could not be set: ", e);
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


    public Path createWoodProjectPath(String username, String projectTitle) {
        PathInfoHolder infoHolder = pathGenerator.getPathInfoHolder();
        infoHolder.setUsername(username);
        infoHolder.setProjectTitle(projectTitle);
        Path woodProjectPath = pathGenerator.generatePath(rootLocation, infoHolder);
        StorageServiceHelper.createDirectory(woodProjectPath);
        return woodProjectPath;
    }

    public boolean copy(Path source, Path target){
        return StorageServiceHelper.copy(source, target);
    }


}
