package com.dunn.dao.user;

import com.dunn.model.WoodProject;
import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.WoodulikeUser;
import org.hibernate.SessionFactory;

import java.util.List;

public interface IUserDAO {

    WoodulikeUser createWoodulikeUser(WoodulikeUser woodulikeUser);

    WoodulikeUser findWoodulikeUser(WoodulikeUser woodulikeUser);

    WoodulikeUser updateWoodulikeUser(WoodulikeUser woodulikeUser);

    void deleteWoodulikeUser(WoodulikeUser woodulikeUser);

    List<WoodulikeUser> allWoodulikeUsers();

    SessionFactory getSessionFactory();

    void setSessionFactory(SessionFactory sessionFactory);

    public WoodulikeUser findWoodulikeUserByUsername(String username);

    public WoodulikeUser findWoodulikeUserByEmailAddress(String emailAddress);


}
