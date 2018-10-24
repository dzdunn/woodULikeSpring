package com.dunn.dao.user;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WoodulikeUser woodulikeUser = userDAO.findWoodulikeUserByUsername(username);
        if(woodulikeUser == null){
            throw new UsernameNotFoundException(username);
        }
        return woodulikeUser;
    }

    public boolean registerUser(WoodulikeUser woodulikeUser){
        userDAO.createWoodulikeUser(woodulikeUser);
        return true;
    }

    public WoodulikeUser loadUserByEmailAddress(String emailAddress){
        return userDAO.findWoodulikeUserByEmailAddress(emailAddress);
    }

}
