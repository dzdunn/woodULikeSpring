package com.dunn.util.pathgenerators;

import java.nio.file.Path;
import java.util.UUID;

public class TempWoodProjectPathGenerator implements PathGenerator {

    PathInfoHolder pathInfoHolder;

    @Override
    public Path generatePath(Path rootLocation, PathInfoHolder pathInfoHolder) {
        return rootLocation
                .resolve(pathInfoHolder.getUsername() + UUID.randomUUID());
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
