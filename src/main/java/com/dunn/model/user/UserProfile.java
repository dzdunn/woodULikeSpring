package com.dunn.model.user;

import javax.persistence.*;

@Entity
public class UserProfile {

    private Long id;

    private WoodulikeUser woodulikeUser;

    private String aboutMe;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @OneToOne(targetEntity = WoodulikeUser.class, cascade = CascadeType.ALL)
    public WoodulikeUser getWoodulikeUser() {
        return woodulikeUser;
    }

    public void setWoodulikeUser(WoodulikeUser woodulikeUser) {
        this.woodulikeUser = woodulikeUser;
    }

}
