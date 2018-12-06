package com.dunn.controller.uipaths.resources;

import java.nio.file.Paths;

public class ResourcePropertiesHolder {

    private final ResourceHandler resourceHandler;

    private final String[] resourceLocations;

    public ResourcePropertiesHolder(final ResourceHandler resourceHandler, String... mappedDirectories){
        this.resourceHandler = resourceHandler;
        this.resourceLocations = mappedDirectories;
    }

    public ResourceHandler getResourceHandler(){
        return resourceHandler;
    }

    public String[] getResourceLocations(){
        return resourceLocations;
    }

    public String[] getResourceLocationsAsUriFormattedString(){
        String[] uriArray = new String[resourceLocations.length];
        for(int i = 0; i < resourceLocations.length; i++){
            uriArray[i] = Paths.get(resourceLocations[i]).toUri().toString();
        }
        return uriArray;
    }

    public ResourcePropertiesHolder getResourcePropertiesHolder(){
        return this;
    }

}
