package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.security.UserSecurityService;
import com.dunn.dao.user.UserService;
import com.dunn.model.GenericResponse;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.validation.login.IWoodulikeUserLoginValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserSecurityService userSecurityService;

    @Value("${support.email}")
    private String supportEmail;

    @RequestMapping(value = ViewName.LOGIN, method = RequestMethod.GET)
    public ModelAndView showLogin(@RequestParam(value = "error", required = false)boolean error){
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("woodulikeUser", new WoodulikeUser());
        return mav;
    }

    @RequestMapping(value = ViewName.LOGIN_PROCESS, method = RequestMethod.POST)
    public ModelAndView loginProcess(@ModelAttribute("woodulikeUser") @Validated(IWoodulikeUserLoginValidationGroup.class) WoodulikeUser woodulikeUser, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()){
//            int errorCount = bindingResult.getErrorCount();
//            for (ObjectError oe: bindingResult.getAllErrors()){
//                System.out.println(oe.getObjectName());
//                System.out.println(oe.getCode());
//                System.out.println(oe.getArguments());
//                System.out.println(oe.getDefaultMessage());
//
//            }
//            mav.addObject("errorCount", errorCount);
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

    @RequestMapping(value=ViewName.RESET_PASSWORD, method = RequestMethod.GET)
    public ModelAndView showResetPassword(@RequestParam(value = "error", required = false)boolean error){
        ModelAndView mav = new ModelAndView(ViewName.RESET_PASSWORD);

        mav.addObject("userEmailAddress", new GenericResponse());
        return mav;
    }

    @RequestMapping(value=ViewName.RESET_PASSWORD_PROCESS, method = RequestMethod.POST)
    public String resetPasswordProcess(@ModelAttribute("emailAddress") GenericResponse emailAddress, HttpServletRequest request){

        //Find user by email address
        WoodulikeUser woodulikeUser = userService.loadUserByEmailAddress(emailAddress.getMessage());
        if(woodulikeUser == null){
            throw new UsernameNotFoundException(emailAddress.getMessage());
        }

        //Generate token and save to database
        String token = UUID.randomUUID().toString();
        userSecurityService.createPasswordResetTokenForUser(woodulikeUser, token);

        //send email containing token
        javaMailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, woodulikeUser));

    //return new GenericResponse(messageSource.getMessage("message.resetPassword", null, request.getLocale()));
        return "redirect:" + ViewName.HOMEPAGE;
    }


    @RequestMapping(value = ViewName.CHANGE_PASSWORD, method = RequestMethod.GET)
    public String showChangePassword(Locale locale, Model model, @RequestParam("id") Long id, @RequestParam("token") String token){
        String result = userSecurityService.validatePasswordResetToken(id, token);
        if(result != null){
            model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));
            return "redirect:" + ViewName.LOGIN + "?lang=" + locale.getLanguage();
        }

        return "redirect:" + ViewName.UPDATE_PASSWORD + "?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = ViewName.UPDATE_PASSWORD, method = RequestMethod.GET)
    public ModelAndView updatePassword(){
        ModelAndView mav = new ModelAndView(ViewName.UPDATE_PASSWORD);
        mav.addObject("newPassword", new GenericResponse());
        return mav;
    }

    @RequestMapping(value = ViewName.UPDATE_PASSWORD_PROCESS, method = RequestMethod.POST)
    public ModelAndView updatePasswordProcess(@ModelAttribute("newPassword") GenericResponse newPassword){

        ModelAndView mav = new ModelAndView("redirect:" + ViewName.LOGIN);
        WoodulikeUser woodulikeUser = (WoodulikeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userSecurityService.changePassword(woodulikeUser, newPassword.getMessage());
        return mav;
    }



    ///PUT IN DIFFERENT CLASS

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, WoodulikeUser user) {
        String url = contextPath + "/user/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = messageSource.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             WoodulikeUser woodulikeUser) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(woodulikeUser.getEmailAddress());
        email.setFrom(supportEmail);
        return email;
    }


}
