package com.dunn.model;

import javax.persistence.*;

//@Entity
public class Photo {

   // @Id
    ///@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private byte[] image;

    private int threadValue;

    private WoodProject woodProject;

    //@ManyToOne(targetEntity = WoodProject.class)
    //@JoinColumn(name = "woodProject_id")
    public WoodProject getWoodProject() {
        return woodProject;
    }

    public void setWoodProject(WoodProject woodProject) {
        this.woodProject = woodProject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getThreadValue(){
        return threadValue;
    }

    public void setThreadValue(int threadValue){
        this.threadValue = threadValue;
    }

}
