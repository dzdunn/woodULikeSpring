package com.dunn.dao.user;


import com.dunn.model.user.WoodulikeUser;

public interface IUserDAO {

    public WoodulikeUser findByUserName(String username);

}
