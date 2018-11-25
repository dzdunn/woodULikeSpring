package com.dunn.dao;

import com.dunn.config.webapp.WebMvcConfig;
import com.dunn.controller.path.resources.ResourceProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;


import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitWebConfig(classes = {WebMvcConfig.class})
public class StorageServiceIT {


    @Test
    public void testDirectory() {
        //fileSystemStorageService.init();
        //assertNotNull(fileSystemStorageService);

    }

    @Test
    public void pathTest() {
        File f = new File("C:\\Users\\dzdun\\Documents\\Java_Projects\\JAVA_RESOURCES\\apache-tomcat-9.0.12\\bin\\upload-dir\\tester-0a22e841-a727-4597-ab2b-6722550a186a");

        URI imagePath = f.toURI();

        Path rootlocation = Paths.get("upload-dir");

        System.out.println(rootlocation.relativize(Paths.get(imagePath.toString())).toString());

    }

    @Value("${storage.location.createwoodproject.permanent}")
    private String test;

    @Test
    public void resourcePropertiesTest(){
        //assertNotNull(resourceProperties);
        System.out.println(ResourceProperties.WOOD_PROJECT_IMAGE_PROPERTIES.getResourceLocations()[0]);
        System.out.println(ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourceLocations()[0]);
        System.out.println(test);

    }
}
