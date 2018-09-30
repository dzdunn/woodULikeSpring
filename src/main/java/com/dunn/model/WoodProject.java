package com.dunn.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class WoodProject implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Date date;
    private List<Image> images;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="WOODPROJECT_ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(targetEntity = Image.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}