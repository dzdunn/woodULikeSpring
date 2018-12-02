package com.dunn.model.user;

import com.dunn.model.WoodProject;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        this.imageDirectories = new ArrayList<>();
    }

    private WoodProject woodProject;

    private Path tempDirectory;

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageHolder) {
        this.imageFile = imageHolder;
    }

    private List<String> imageDirectories;

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

    public List<String> getImageDirectories() {
        return imageDirectories;
    }

    public void setImageDirectories(List<String> imageDirectories) {
        this.imageDirectories = imageDirectories;
    }

    public void addImageDirectory(String imageDirectory){
        this.imageDirectories.add(imageDirectory);
    }
}
