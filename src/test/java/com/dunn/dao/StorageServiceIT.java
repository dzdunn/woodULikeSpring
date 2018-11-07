package com.dunn.dao;

import com.dunn.config.WebMvcConfig;
import com.dunn.model.storage.IStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitWebConfig(classes = WebMvcConfig.class)
public class StorageServiceIT {

    @Autowired
    private IStorageService fileSystemStorageService;

    @Test
    public void testDirectory(){
        //fileSystemStorageService.init();
        assertNotNull(fileSystemStorageService);

    }
}
