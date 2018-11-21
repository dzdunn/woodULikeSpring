package com.dunn.controller.path.resources;

public class ResourcePropertiesHolder {

    private final ResourceHandler resourceHandler;

    private final String[] mappedDirectories;

    public ResourcePropertiesHolder(final ResourceHandler resourceHandler, String... mappedDirectories){
        this.resourceHandler = resourceHandler;
        this.mappedDirectories = mappedDirectories;
    }

    public ResourceHandler getResourceHandler(){
        return resourceHandler;
    }

    public String[] getMappedDirectories(){
        return mappedDirectories;
    }

}
