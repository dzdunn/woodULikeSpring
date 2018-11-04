package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.security.UserSecurityService;
import com.dunn.dao.user.UserService;
import com.dunn.model.GenericResponse;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.validation.login.IWoodulikeUserLoginValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

@Controller
public class LoginController {


    @RequestMapping(value = ViewName.LOGIN, method = RequestMethod.GET)
    public ModelAndView showLogin(){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.LOGIN_PROCESS, method = RequestMethod.POST)
    public ModelAndView loginProcess(@ModelAttribute("woodulikeUser") @Validated(IWoodulikeUserLoginValidationGroup.class) WoodulikeUser woodulikeUser, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();

        if(bindingResult.hasErrors()){
            String key = UUID.randomUUID().toString();
            //Should find a better way of enforcing this role for failed login attempts
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContextHolder.getContext().setAuthentication( new AnonymousAuthenticationToken(key, "anonymousUser", Collections.singletonList(new UserRole("ROLE_ANONYMOUS"))));
            }
            mav.setViewName(ViewName.LOGIN);
            return mav;
        }

        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            mav.setViewName("redirect:" + ViewName.HOMEPAGE);
        } else{
            mav.setViewName("redirect:" + ViewName.LOGIN);
        }
        return mav;
    }




}
