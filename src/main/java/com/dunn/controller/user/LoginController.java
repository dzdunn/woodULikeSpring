package com.dunn.controller.user;

import com.dunn.controller.uipaths.views.ViewName;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @ModelAttribute
    public WoodulikeUser woodulikeUser(){
        return new WoodulikeUser();
    }

    @RequestMapping(value = ViewName.LOGIN, method = RequestMethod.GET)
    public ModelAndView showLogin(){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        return mav;
    }

    @RequestMapping(value = ViewName.LOGIN_FAILURE, method = RequestMethod.GET)
    public ModelAndView failLogin(@SessionAttribute("bindingResult") BindingResult bindingResult, SessionStatus sessionStatus){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addAllObjects(bindingResult.getModel());
        sessionStatus.setComplete();
        return mav;
    }

}
