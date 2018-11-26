package com.dunn.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RunWith(BlockJUnit4ClassRunner.class)
public class BasicTest {

    @Test
    public void pathTester(){
        String rootLocation = "rootLocation";
        String username = "tester";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss"));
        String projectTitle = "projectTitle";

        Path testPath = Paths.get(rootLocation).resolve(username).resolve(projectTitle + "-" + date + UUID.randomUUID()).toAbsolutePath();



        System.out.println(testPath);
    }
}
