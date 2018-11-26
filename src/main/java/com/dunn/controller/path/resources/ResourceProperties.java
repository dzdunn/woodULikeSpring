package com.dunn.controller.path.resources;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceProperties implements InitializingBean {

    @Value("${storage.location.createwoodproject.temp}")
    private String[] createWoodProjectTempImageDirectory;

    @Value("${storage.location.createwoodproject.permanent}")
    private String[] createWoodProjectPermanentImageDirectory;

    @Value("${resources.location.static}")
    private String[] staticWebResourcesDirectory;

    @Value("${resources.location.img}")
    private String[] staticImageWebResourcesDirectory;

    private ResourceProperties(){

    }

    public static ResourcePropertiesHolder STATIC_PROPERTIES;

    public static ResourcePropertiesHolder IMG_PROPERTIES;

    public static ResourcePropertiesHolder CREATE_WOOD_PROJECT_TEMP_PROPERTIES;

    public static ResourcePropertiesHolder WOOD_PROJECT_IMAGE_PROPERTIES;

    public ResourcePropertiesHolder getResourcePropertiesHolder(){
        return this.getResourcePropertiesHolder();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        STATIC_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.STATIC, staticWebResourcesDirectory);
        IMG_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.IMG, staticImageWebResourcesDirectory);


        WOOD_PROJECT_IMAGE_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.WOOD_PROJECT_IMAGES, createWoodProjectPermanentImageDirectory);
        CREATE_WOOD_PROJECT_TEMP_PROPERTIES = new ResourcePropertiesHolder(ResourceHandler.CREATE_WOOD_PROJECT_TEMP, createWoodProjectTempImageDirectory);

    }
}
