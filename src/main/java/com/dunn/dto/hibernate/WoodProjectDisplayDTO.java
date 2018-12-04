package com.dunn.dto.hibernate;

import com.dunn.model.woodproject.Image;

import java.util.List;

public class WoodProjectDisplayDTO {

    private String projectTitle;

    private String username;

    private String description;

    private List<Image> images;

    public WoodProjectDisplayDTO(String projectTitle, String username, String description, List<Image> images) {
        this.projectTitle = projectTitle;
        this.username = username;
        this.description = description;
        this.images = images;
    }
}
