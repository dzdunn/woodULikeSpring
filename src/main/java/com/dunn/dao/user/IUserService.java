package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User validateUser(WoodulikeUser woodulikeUser);

    boolean register(WoodulikeUser woodulikeUser);

}
