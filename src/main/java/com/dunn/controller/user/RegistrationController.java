package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.user.IUserService;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegistrationController {

    @Autowired
    public IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = ViewName.REGISTER, method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView(ViewName.REGISTER);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.REGISTER_PROCESS, method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser){
        boolean isRegistrationSuccessful = userService.register(woodulikeUser);
        woodulikeUser.setPassword(passwordEncoder.encode(woodulikeUser.getPassword()));
        ModelAndView mav = new ModelAndView();

        if(isRegistrationSuccessful){
            mav.setViewName(ViewName.LOGIN);
            return mav;
        } else {
            mav.setViewName(ViewName.REGISTER);
            return mav;
        }
    }
}
