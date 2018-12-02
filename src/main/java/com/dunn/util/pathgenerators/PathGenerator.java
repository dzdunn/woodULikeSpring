package com.dunn.util.pathgenerators;

import java.nio.file.Path;

public interface PathGenerator {

   Path generatePath(Path rootLocation, PathInfoHolder directoryPrefix);

   PathInfoHolder getPathInfoHolder();

   void setPathInfoHolder(PathInfoHolder pathInfoHolder);

}
