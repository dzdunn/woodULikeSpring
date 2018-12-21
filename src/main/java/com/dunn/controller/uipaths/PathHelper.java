package com.dunn.controller.uipaths;

import com.dunn.controller.uipaths.resources.ResourcePropertiesHolder;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathHelper {

    private PathHelper(){

    }

    public static Path replaceRootWithResourceHandler(Path pathToReplace, ResourcePropertiesHolder resourcePropertiesHolder){

        String[] resourceLocations = resourcePropertiesHolder.getResourceLocations();

        StringBuilder regexBuilder = new StringBuilder();
        regexBuilder.append(".*");

        for(int i = 0; i < resourceLocations.length; i++){
            String location = resourceLocations[i].replaceAll("/", "");
            regexBuilder.append(location);
            if(resourceLocations.length > 1 && i != resourceLocations.length-1){
                regexBuilder.append("|.*");
            }
        }

        String resourceHandlerReplacement = removePatternMatchersAndBackSlashes(resourcePropertiesHolder.getResourceHandler().getResourceHandlerString());
        String path = pathToReplace.toString().replaceAll(regexBuilder.toString(), resourceHandlerReplacement);

        return Paths.get(path);
    }

    public static String replaceRootWithResourceHandlerWithForwardSlash(Path pathToReplace, ResourcePropertiesHolder resourcePropertiesHolder){
        return replaceBackSlashWithForwardSlash(replaceRootWithResourceHandler(pathToReplace, resourcePropertiesHolder));
    }

    public static Path getFileNamePath(String fileName, Path rootPath){
        return rootPath.resolve(fileName);
    }

    public static String removePatternMatchersAndBackSlashes(String patternMatchedDirectory){
        return patternMatchedDirectory.replaceAll("/|\\*", "");
    }

    public static String replaceBackSlashWithForwardSlash(Path pathToReplace){
        return pathToReplace.toString().replaceAll("\\\\", "/");
    }
}
