package com.dunn.util.storage;

import com.dunn.util.pathgenerators.PathGenerator;
import com.dunn.util.pathgenerators.PathInfoHolder;
import com.dunn.util.pathgenerators.TempWoodProjectPathGenerator;
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
public class TempWoodProjectImageStorageService implements IStorageService {

    private final Path rootLocation;

    private final PathGenerator pathGenerator = new TempWoodProjectPathGenerator();

    private static final Logger LOGGER = LoggerFactory.getLogger(TempWoodProjectImageStorageService.class);

    public TempWoodProjectImageStorageService(@Qualifier("storageServiceProperties") PropertiesFactoryBean storageServiceProperties){
        try {
            this.rootLocation = Paths.get(storageServiceProperties.getObject().getProperty("storage.location.createwoodproject.temp"));
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
        return StorageServiceHelper.loadAsResource(rootLocation,filename);
    }

    @Override
    public void deleteAll() {
        StorageServiceHelper.deleteAll(rootLocation);
    }


    public Path createTempWoodProjectPath(String username){
        PathInfoHolder pathInfoHolder = pathGenerator.getPathInfoHolder();
        pathInfoHolder.setUsername(username);
        Path tempPath = pathGenerator.generatePath(rootLocation, pathInfoHolder);
        StorageServiceHelper.createDirectory(tempPath);
        return tempPath;
    }

    @Override
    public Path getRootLocation() {
        return rootLocation;
    }

    @Override
    public void deleteDirectory(Path directoryToDelete) {
        StorageServiceHelper.deleteDirectory(directoryToDelete);
    }
}
