package com.dunn.util.pathgenerators;

import com.dunn.util.storage.StorageServiceHelper;

import java.nio.file.Path;
import java.util.UUID;

public class TempWoodProjectPathGenerator implements PathGenerator {

    PathInfoHolder pathInfoHolder;

    @Override
    public Path generatePath(Path rootLocation, PathInfoHolder directoryPrefix) {
        return rootLocation
                .resolve(directoryPrefix.getUsername() + UUID.randomUUID());
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
