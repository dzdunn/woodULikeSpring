package com.dunn.util.pathgenerators;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class WoodProjectPathGenerator implements PathGenerator {

    private PathInfoHolder pathInfoHolder;

    @Override
    public Path generatePath(Path rootLocation, PathInfoHolder pathInfoHolder) {
        return rootLocation
                .resolve(pathInfoHolder.getUsername() + UUID.randomUUID())
                .resolve(pathInfoHolder.getProjectTitle() + UUID.randomUUID())
                .resolve(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss")));
    }

    public PathInfoHolder getPathInfoHolder(){
        if(pathInfoHolder == null){
            pathInfoHolder = new PathInfoHolder();
        }
        return pathInfoHolder;
    }

    public void setPathInfoHolder(PathInfoHolder pathInfoHolder){
        this.pathInfoHolder = pathInfoHolder;
    }
}
