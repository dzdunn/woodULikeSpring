package com.dunn.model.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class CreateWoodProjectTempImageStorageService implements IStorageService {

    private final Path rootLocation;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateWoodProjectTempImageStorageService.class);

    public CreateWoodProjectTempImageStorageService(@Qualifier("storageServiceProperties") PropertiesFactoryBean storageServiceProperties){
        try {
            this.rootLocation = Paths.get(storageServiceProperties.getObject().getProperty("storage.location.createwoodproject.temp"));
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
    public Path storeToUniqueDirectory(MultipartFile file, String username, Path tempDirectoryTarget){
       return StorageServiceHelper.storeToUniqueDirectory(rootLocation, file, username, tempDirectoryTarget);
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
        return StorageServiceHelper.loadAsResource(rootLocation,filename);
    }

    @Override
    public void deleteAll() {
        StorageServiceHelper.deleteAll(rootLocation);
    }

    @Override
    public Path generateUniqueDirectory(String username){
       return StorageServiceHelper.generateUniqueDirectory(rootLocation, username);
    }

    @Override
    public void transferToUserProjectDirectory(){

    };

    @Override
    public Path getRootLocation() {
        return rootLocation;
    }
}
