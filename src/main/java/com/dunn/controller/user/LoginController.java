package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.user.UserService;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = ViewName.LOGIN, method = RequestMethod.GET)
    public ModelAndView showLogin(@RequestParam(value = "error", required = false)boolean error){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.LOGIN_PROCESS, method = RequestMethod.POST)
    public ModelAndView loginProcess(@ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser){
        return new ModelAndView(ViewName.HOME);
    }


}
