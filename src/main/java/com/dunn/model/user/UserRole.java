package com.dunn.model.user;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"role", "username"}))
public class UserRole {

    private Long id;

    private WoodulikeUser woodulikeUser;

    private String role;

    public UserRole(){

    }

    public UserRole(WoodulikeUser woodulikeUser, String role){
        this.woodulikeUser = woodulikeUser;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public WoodulikeUser getWoodulikeUser() {
        return woodulikeUser;
    }

    public void setWoodulikeUser(WoodulikeUser user) {
        this.woodulikeUser = user;
    }

    @Column(name="role", nullable = false, length = 45)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
