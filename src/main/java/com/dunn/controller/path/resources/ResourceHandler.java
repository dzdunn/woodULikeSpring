package com.dunn.controller.path.resources;

public enum ResourceHandler {

    STATIC("/static/**"),
    IMG("/img/**"),
    CREATE_WOOD_PROJECT_TEMP("/createWoodProjectTemp/**"),
    WOOD_PROJECT_IMAGES("/woodProjectImages/**");

    private final String resourceHandlerString;

    ResourceHandler(String resourceHandlerString){
        this.resourceHandlerString = resourceHandlerString;
    }

    public String getResourceHandlerString() {
        return resourceHandlerString;
    }

}
