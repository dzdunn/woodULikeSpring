package com.dunn.util.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
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
                Files.copy(inputStream, targetDirectory.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + targetDirectory.toString(), e);
        }
    }


    public static Stream<Path> loadAll(Path rootLocation) {
        try {
           // return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
            return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation));
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
            if (Files.exists(rootLocation)) {
                FileSystemUtils.deleteRecursively(rootLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirectory(Path directoryToDelete) {

        try {
            if (Files.exists(directoryToDelete)) {
                FileSystemUtils.deleteRecursively(directoryToDelete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectory(Path tempDirectory){
        if (!Files.exists(tempDirectory)) {
            try {
                Files.createDirectories(tempDirectory);
            } catch (IOException e) {
                //add logging
                throw new StorageException("Could not create user temp directory for gallery: ", e);
            }
        }
    }

    public static boolean copy(Path source, Path target) {
        try {
            FileSystemUtils.copyRecursively(source, target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return hasSameFileNamesInDirectoryies(source, target);

    }

    public static boolean hasSameFileNamesInDirectoryies(Path source, Path target) {
        {
            try (Stream<Path> sourceFiles = Files.walk(source);
                 Stream<Path> targetFiles = Files.walk(target)) {
                List<String> sourceFileNames = sourceFiles.filter(x -> !Files.isDirectory(x)).map(x -> x.getFileName().toString()).collect(Collectors.toList());
                List<String> targetFileNames = targetFiles.filter(x -> !Files.isDirectory(x)).map(x -> x.getFileName().toString()).collect(Collectors.toList());

                for (String sourceFileName : sourceFileNames) {

                    if (!targetFileNames.contains(sourceFileName)) {
                        return false;
                    }
                }

            } catch (IOException e) {
                //add logging
            }
            return true;
        }
    }
}
