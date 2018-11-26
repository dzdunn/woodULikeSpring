package com.dunn.model.storage;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.Nullable;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorageServiceHelper {

    public static void initiateDirectory(Path rootLocation) {
        if (!Files.exists(rootLocation))
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage directory: ", e);
            }
    }

    public static void store(MultipartFile file, Path targetDirectory) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file: " + targetDirectory.getFileName());
            }
            if (targetDirectory.toString().contains("..")) {
                throw new StorageException("Cannot store file with relative path outside current directory: " + targetDirectory.toString());
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + targetDirectory.toString(), e);
        }
    }

    /**
     * Pass null tempDirectory to automatically generate a unique tempDirectory, then store file to that directory.
     * Otherwise, file will be stored to the tempDirectory explicitly passed.
     *
     * @param file                - the file to be stored
     * @param tempDirectoryPrefix - the prefix to the unique temp directory
     * @param directoryTarget     - the tempDirectory in which to store the file
     * @return
     */
    public static Path storeToUniqueDirectory(Path rootLocation, MultipartFile file, String tempDirectoryPrefix, Path directoryTarget) {

        if (directoryTarget == null) {
            directoryTarget = generateUniqueDirectory(rootLocation, tempDirectoryPrefix);
        }

        store(file, directoryTarget.resolve(file.getOriginalFilename()));

        return directoryTarget;
    }

    public static Stream<Path> loadAll(Path rootLocation) {
        try {
            return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files: ", e);
        }
    }

    public static Path load(Path rootLocation, String filename) {
        return rootLocation.resolve(filename);
    }

    public static Resource loadAsResource(Path rootLocation, String filename) {
        try {
            Path file = load(rootLocation, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read filename: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read filename: " + filename, e);
        }
    }

    public static void deleteAll(Path rootLocation) {

        try {
            if(Files.exists(rootLocation)) {
                FileSystemUtils.deleteRecursively(rootLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path generateUniqueDirectory(Path rootLocation, String directoryPrefix) {

        Path tempFolder = rootLocation.resolve(directoryPrefix + "-" + UUID.randomUUID());
        System.out.println(tempFolder.toAbsolutePath().toString());
        if (!Files.exists(tempFolder)) {
            try {
                Files.createDirectories(tempFolder);
            } catch (IOException e) {
                throw new StorageException("Could not create user temp directory for gallery: ", e);
            }
        }
        return tempFolder;
    }

    public static void deleteDirectory(Path directoryToDelete) {

        try {
           if(Files.exists(directoryToDelete)) {
               FileSystemUtils.deleteRecursively(directoryToDelete);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean copy(Path source, Path target){
        boolean result = false;

        try {
            FileSystemUtils.copyRecursively(source, target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try(Stream<Path> sourceFiles = Files.walk(source);
            Stream<Path> targetFiles = Files.walk(target)){
            List<String> sourceFileNames = sourceFiles.filter(x -> !Files.isDirectory(x)).map(x -> x.getFileName().toString()).collect(Collectors.toList());
            List<String> targetFileNames = targetFiles.filter(x -> !Files.isDirectory(x)).map(x -> x.getFileName().toString()).collect(Collectors.toList());

            for(String sourceFileName: sourceFileNames){
                result = targetFileNames.contains(sourceFileName);
                if(!result){
                    return result;
                }
            }


        } catch (IOException e){

        }
        return result;

    }

//    static public boolean deleteDirectory(File path) {
//        if (path.exists()) {
//            File[] files = path.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isDirectory()) {
//                    deleteDirectory(files[i]);
//                } else {
//                    files[i].delete();
//                }
//            }
//        }
//        return (path.delete());
//    }
}
