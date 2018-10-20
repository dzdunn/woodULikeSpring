package com.dunn.dao.user;

import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WoodulikeUser woodulikeUser = userDao.findByUserName(username);
        List<GrantedAuthority> authorities = buildUserAuthority(woodulikeUser.getUserRole());
        return buildUserForAuthentication(woodulikeUser, authorities);
    }

    private User buildUserForAuthentication(WoodulikeUser woodulikeUser, List<GrantedAuthority> authorities) {
        return new User(woodulikeUser.getUsername(), passwordEncoder.encode(woodulikeUser.getPassword()),
                woodulikeUser.isEnabled(), true, true,
                true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        List<GrantedAuthority> result = new ArrayList<>(setAuths);
        return result;
    }

    @Override
    public User validateUser(WoodulikeUser woodulikeUser) {
        UserDetails userDetails = loadUserByUsername(woodulikeUser.getUsername());
        if (userDetails != null && passwordEncoder.encode(woodulikeUser.getPassword()).equals(passwordEncoder.encode(userDetails.getPassword()))) {


            User user = buildUserForAuthentication(woodulikeUser, buildUserAuthority(woodulikeUser.getUserRole()));
            return user;
        }
        return null;
    }

    @Override
    public boolean register(WoodulikeUser woodulikeUser){
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        //woodulikeUser.setUserRole(userRoleSet);
        woodulikeUser.setEnabled(true);
        userRole.setWoodulikeUser(woodulikeUser);
        return userDao.register(woodulikeUser);
    }
}
