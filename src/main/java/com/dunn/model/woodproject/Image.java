package com.dunn.model.woodproject;

import javax.persistence.*;
import java.io.*;

@Entity
public class Image implements Serializable {

    private Long id;
    private String imageName;
    private String path;

    private String storedDirectory;
    private WoodProject woodProject;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="IMAGE_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "woodProject_id")
    public WoodProject getWoodProject() {
        return woodProject;
    }

    public void setWoodProject(WoodProject woodProject) {
        this.woodProject = woodProject;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStoredDirectory() {
        return storedDirectory;
    }

    public void setStoredDirectory(String storedDirectory) {
        this.storedDirectory = storedDirectory;
    }

}
