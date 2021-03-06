package com.dunn.dao.user;

import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "userService")
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

    public boolean isUsernameRegistered(String username){
        return userDAO.isUsernameTaken(username);
    }

    public boolean isEmailRegistered(String emailAddress){
        return userDAO.isEmailRegistered(emailAddress);
    }

    public boolean isUsernameAndPasswordValid(String username, String password){
        return userDAO.isUsernameAndPasswordCorrect(username, password);
    }

}
