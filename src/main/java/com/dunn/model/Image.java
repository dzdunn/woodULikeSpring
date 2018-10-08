package com.dunn.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Entity
public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IMAGE_ID", unique = true, nullable = false)
    private Long id;
    private String imageName;
    private String path;

    private byte[] blob;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public static Image createImageFromPath(String path){
        Image image = new Image();
        image.setPath(path);

        File file = new File(path);
        image.setImageName(file.getName());

        try {
            byte[] data = Files.readAllBytes(file.toPath());
            image.setBlob(data);
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }



}
