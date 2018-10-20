package com.dunn.dao.user;


import com.dunn.model.user.WoodulikeUser;

public interface IUserDAO {

    WoodulikeUser findByUserName(String username);

    boolean register(WoodulikeUser woodulikeUser);

}
