package com.dunn.dto.ui;

import com.dunn.controller.uipaths.PathHelper;
import com.dunn.controller.uipaths.resources.ResourceProperties;
import com.dunn.controller.uipaths.resources.ResourcePropertiesHolder;
import com.dunn.model.woodproject.WoodProject;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WoodProjectDTO {

    public WoodProjectDTO(){
    }

    public WoodProjectDTO(ResourcePropertiesHolder resourcePropertiesHolder){
        this.resourcePropertiesHolder = resourcePropertiesHolder;
    }

    public WoodProjectDTO(WoodProject woodProject, ResourcePropertiesHolder resourcePropertiesHolder){
        this.username = woodProject.getWoodulikeUser().getUsername();
        this.imagePaths= woodProject.getImages().stream().map(x -> Paths.get(x.getPath())).collect(Collectors.toList());
        this.resourcePropertiesHolder = resourcePropertiesHolder;
        this.woodProject = woodProject;
    }

    private ResourcePropertiesHolder resourcePropertiesHolder = ResourceProperties.WOOD_PROJECT_IMAGE_PROPERTIES;

    private String username;

    private WoodProject woodProject;

    private Path tempDirectory;

    private List<Path> imagePaths = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageHolder) {
        this.imageFile = imageHolder;
    }

    private MultipartFile imageFile;


    public WoodProject getWoodProject() {
        return woodProject;
    }

    public void setWoodProject(WoodProject woodProject) {
        this.woodProject = woodProject;
    }

    public Path getTempDirectory() {
        return tempDirectory;
    }

    public void setTempDirectory(Path tempDirectory) {
        this.tempDirectory = tempDirectory;
    }

    public List<String> getRelativeImagePaths(){
       return imagePaths.stream().map(absolutePath ->
                    PathHelper.replaceRootWithResourceHandlerWithForwardSlash(absolutePath, resourcePropertiesHolder)
                ).collect(Collectors.toList());
    }

    public List<Path> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<Path> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void addImagePath(Path imagePath){
        this.imagePaths.add(imagePath);
    }

    public ResourcePropertiesHolder getResourcePropertiesHolder() {
        return resourcePropertiesHolder;
    }

    public void setResourcePropertiesHolder(ResourcePropertiesHolder resourcePropertiesHolder) {
        this.resourcePropertiesHolder = resourcePropertiesHolder;
    }
}
