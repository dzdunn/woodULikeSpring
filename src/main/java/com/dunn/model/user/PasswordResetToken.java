package com.dunn.model.user;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    public PasswordResetToken(){

    }

    public PasswordResetToken(WoodulikeUser woodulikeUser, String token){
        this.woodulikeUser = woodulikeUser;
        this.token = token;
        this.expiryDate = LocalDate.now().plusDays(1);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = WoodulikeUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private WoodulikeUser woodulikeUser;

    private LocalDate expiryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WoodulikeUser getWoodulikeUser() {
        return woodulikeUser;
    }

    public void setWoodulikeUser(WoodulikeUser woodulikeUser) {
        this.woodulikeUser = woodulikeUser;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
