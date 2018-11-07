package com.dunn.config;

import com.dunn.dao.user.UserService;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@Profile("dev")
public class DevDataSetup implements ApplicationListener<ContextRefreshedEvent> {



    private UserService userService;



    @Autowired
    public DevDataSetup(@Lazy UserService userService){
        this.userService = userService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        WoodulikeUser woodulikeUser = new WoodulikeUser();
        woodulikeUser.setUsername("tester");
        woodulikeUser.setPassword("testing1!");
        woodulikeUser.setCountry("United Kingdom");
        woodulikeUser.setDateOfBirth(LocalDate.parse("1990-10-01"));
        woodulikeUser.setEmailAddress("dzdunn@gmail.com");
        woodulikeUser.setFirstName("Tester");
        woodulikeUser.setLastName("Testee");
        woodulikeUser.setUserRoles(Arrays.asList(new UserRole("ROLE_USER")));
        woodulikeUser.setEnabled(true);
        woodulikeUser.setAccountNonExpired(true);
        woodulikeUser.setCredentialsNonExpired(true);
        woodulikeUser.setAccountNonLocked(true);

        userService.registerUser(woodulikeUser);
    }
}
