package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String test(){
//        return "/home/home";
//    }

    @RequestMapping(value = ViewName.LOGIN, method = RequestMethod.GET)
    public ModelAndView showLogin(@RequestParam(value = "error", required = false)boolean error){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.LOGIN_PROCESS, method = RequestMethod.POST)
    public ModelAndView loginProcess(@ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser){
        ModelAndView mav = new ModelAndView();
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            mav.setViewName("redirect:" + ViewName.HOMEPAGE);
        } else{
            mav.setViewName("redirect:" + ViewName.LOGIN);
        }
        return mav;
    }

//    @RequestMapping(value=ViewName.LOGOUT, method = RequestMethod.GET)
//    public ModelAndView logoutProcess(){
//        ModelAndView mav = new ModelAndView("redirect:" + ViewName.LOGIN);
//        return mav;
//    }


}
