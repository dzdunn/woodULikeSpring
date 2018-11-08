package com.dunn.model.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements IStorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Qualifier("storageServiceProperties") PropertiesFactoryBean storageServiceProperties){
        try {
            this.rootLocation = Paths.get(storageServiceProperties.getObject().getProperty("storage.location"));
        } catch (IOException e) {
           throw new StorageException("Storage path could not be set: ", e);
        }
    }

    @Override
    @PostConstruct
    public void init() {
        if (!Files.exists(rootLocation))
            try {
                Files.createDirectories(rootLocation);
                System.out.println("ITS HEREEEEE" + rootLocation.toAbsolutePath());
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage directory: ", e);
            }
    }


    @Override
    public void store(MultipartFile file, Path targetDirectory) {
        //String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file: " + targetDirectory.getFileName());
            }
            if(targetDirectory.toString().contains("..")){
                throw new StorageException("Cannot store file with relative path outside current directory: " + targetDirectory.toString());
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(IOException e){
            throw new StorageException("Failed to store file: " + targetDirectory.toString(), e);
        }
    }

    @Override
    public Path storeToTempDirectory(MultipartFile file, String username){
        Path targetDirectory = generateUniqueTempImageDirectory(username);
        store(file, targetDirectory.resolve(file.getOriginalFilename()));

        return targetDirectory;
    }

    //MAKE THIS RETURN UNIQUE PATH I.E USER, PROJECT, IMAGE
    private String generateUniquePath(String username, String projectTitle, String imageName){
        return "";
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
        } catch (IOException e){
            throw new StorageException("Failed to read stored files: ", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read filename: " + filename);
            }
        } catch(MalformedURLException e){
            throw new StorageFileNotFoundException("Could not read filename: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Path generateUniqueTempImageDirectory(String username){

        Path usernameSubFolder = rootLocation.resolve(username + "-" + UUID.randomUUID());
        System.out.println(usernameSubFolder.toAbsolutePath().toString());
        if(!Files.exists(usernameSubFolder)) {
            try {
                Files.createDirectories(usernameSubFolder);
            } catch (IOException e) {
                throw new StorageException("Could not create user temp directory for gallery: ", e);
            }
        }

        return usernameSubFolder;
    }

    @Override
    public void transferToUserProjectDirectory(){

    };

    @Override
    public Path getRootLocation() {
        return rootLocation;
    }
}
