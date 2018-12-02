package com.dunn.model.user;

import com.dunn.controller.path.PathHelper;
import com.dunn.controller.path.resources.ResourceProperties;
import com.dunn.model.WoodProject;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WoodProjectDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WoodProjectDTO(){
        this.imagePaths = new ArrayList<>();
    }

    private WoodProject woodProject;

    private Path tempDirectory;

    private List<Path> imagePaths;

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
                    PathHelper.replaceRootWithResourceHandlerWithForwardSlash(absolutePath, ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourcePropertiesHolder())
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
}
