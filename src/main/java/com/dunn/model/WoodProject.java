package com.dunn.model;


import com.dunn.model.user.WoodulikeUser;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class WoodProject implements Serializable {

    private Long id;

    private WoodulikeUser woodulikeUser;

    private String title;
    private String description;
    private Date date;

    private List<Image> images;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(targetEntity = Image.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "woodProject")
    public List<Image> getImages() {
        if(images == null){
            images = new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @ManyToOne(targetEntity = WoodulikeUser.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="woodulikeUser_id")
    public WoodulikeUser getWoodulikeUser() {
        return woodulikeUser;
    }

    public void setWoodulikeUser(WoodulikeUser woodulikeUser) {
        this.woodulikeUser = woodulikeUser;
    }
}
