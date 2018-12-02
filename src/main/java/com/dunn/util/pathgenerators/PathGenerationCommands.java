package com.dunn.util.pathgenerators;

import com.dunn.util.storage.StorageException;
import com.dunn.util.storage.StorageServiceHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PathGenerationCommands {

    public static Path createTempWoodProjectPath(Path rootLocation, String username){
        Path tempPath = rootLocation.resolve(username + "-" + UUID.randomUUID());
        //log created path
        StorageServiceHelper.createDirectory(tempPath);
        return tempPath;
    }

    public static Path createWoodProjectPath(Path rootLocation, String username, String projectTitle){
        Path woodProjectPath = rootLocation
                .resolve(username + UUID.randomUUID())
                .resolve(projectTitle + UUID.randomUUID())
                .resolve(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")));
        StorageServiceHelper.createDirectory(woodProjectPath);
        return woodProjectPath;
    }
}
