package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.user.IUserDAO;
import com.dunn.dao.user.IUserService;
import com.dunn.dao.user.UserService;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {


    @Autowired
    private IUserService userService;

    @RequestMapping(value = ViewName.LOGIN ,method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value= ViewName.LOGIN_PROCESS, method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser) {
        ModelAndView mav = null;
        User user = userService.validateUser(woodulikeUser);
        if (null != user) {
            mav = new ModelAndView(ViewName.HOME);


        } else {
            mav = new ModelAndView(ViewName.LOGIN);
            mav.addObject("message", "Username or Password is wrong!!");
        }
        return mav;
    }
}
