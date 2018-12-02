package com.dunn.config.schduler.jobs;

import com.dunn.util.storage.TempWoodProjectImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class TempFileCleanUpJob implements Runnable {

    @Autowired
    public TempWoodProjectImageStorageService tempWoodProjectImageStorageService;

    private static final long idleTime = 3000L;

    @Override //Once every hour - this should be improved into a more sustainable scheduler model
    @Scheduled(fixedRate = 3600000L)
    public void run() {

        try (Stream<Path> paths = tempWoodProjectImageStorageService.loadAll()) {
            paths.filter(path -> Files.isDirectory(path)).filter(path -> path.toFile().lastModified() < (System.currentTimeMillis() - idleTime)).forEach(path -> tempWoodProjectImageStorageService.deleteDirectory(path));
        }
    }
}
