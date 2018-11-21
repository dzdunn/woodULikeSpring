package com.dunn.controller.path.resources;

import com.dunn.model.storage.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceProperties {

    @Autowired
    private static IStorageService createWoodProjectTempImageStorageService;

    @Autowired
    private static IStorageService woodProjectImageStorageService;

    public static final ResourcePropertiesHolder STATIC_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.STATIC, "/resources/", "/webjars/");

    public static final ResourcePropertiesHolder IMG_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.IMG, "/resources/img/");

    public static final ResourcePropertiesHolder CREATE_WOOD_PROJECT_TEMP_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.CREATE_WOOD_PROJECT_TEMP, "");

    public static final ResourcePropertiesHolder WOOD_PROJECT_IMAGE_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.WOOD_PROJECT_IMAGES, "");
}
