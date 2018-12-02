package com.dunn.util.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {

    void init();

    void store(MultipartFile file, Path targetDirectory);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void deleteDirectory(Path directoryToDelete);

    Path getRootLocation();

}
