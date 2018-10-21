package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.user.UserService;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = ViewName.REGISTER, method = RequestMethod.GET)
    public ModelAndView showRegister(@RequestParam(value = "error", required = false) Boolean error){
        ModelAndView mav = new ModelAndView(ViewName.REGISTER);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.REGISTER_PROCESS, method = RequestMethod.POST)
    public ModelAndView registerProcess(@ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser){
        ModelAndView mav = new ModelAndView();
        woodulikeUser.setUserRoles(Arrays.asList(new UserRole("ROLE_USER")));
        woodulikeUser.setPassword(passwordEncoder.encode(woodulikeUser.getPassword()));
        woodulikeUser.setEnabled(true);
        woodulikeUser.setAccountNonExpired(true);
        woodulikeUser.setCredentialsNonExpired(true);
        woodulikeUser.setAccountNonLocked(true);
        boolean isRegistered = userService.registerUser(woodulikeUser);
        if(isRegistered) {
           mav.setViewName("redirect:" + ViewName.LOGIN);
           return mav;
        } else {
           mav.setViewName("redirect:" + ViewName.REGISTER + "?error=true");
           return mav;
        }
    }
}
