package com.dunn.controller.user;

import com.dunn.controller.path.ViewName;
import com.dunn.dao.security.UserSecurityService;
import com.dunn.dao.user.UserService;
import com.dunn.model.genericmodelwrappers.GenericEmailWrapper;
import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.TmpUserWrapper;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.validation.resetpassword.IForgotPasswordEmailValidationGroup;
import com.dunn.validation.resetpassword.IResetPasswordValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ResetPasswordController {

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



    @RequestMapping(value = ViewName.RESET_PASSWORD, method = RequestMethod.GET)
    public ModelAndView showResetPassword() {
        ModelAndView mav = new ModelAndView(ViewName.RESET_PASSWORD);

        mav.addObject("emailAddressWrapper", new GenericEmailWrapper());
        return mav;
    }

    @RequestMapping(value = ViewName.RESET_PASSWORD_PROCESS, method = RequestMethod.POST)
    public String resetPasswordProcess(@ModelAttribute("emailAddressWrapper") @Validated(IForgotPasswordEmailValidationGroup.class) GenericEmailWrapper emailAddressWrapper, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ViewName.RESET_PASSWORD;
        }
        //Find user by email address
        WoodulikeUser woodulikeUser = userService.loadUserByEmailAddress(emailAddressWrapper.getEmailAddress());
        if (woodulikeUser == null) {
            throw new UsernameNotFoundException(emailAddressWrapper.getEmailAddress());
        }

        //Generate token and save to database
        String token = UUID.randomUUID().toString();
        userSecurityService.createPasswordResetTokenForUser(woodulikeUser, token);

        //send email containing token
        javaMailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, woodulikeUser));

        //return new GenericResponse(messageSource.getMessage("message.resetPassword", null, request.getLocale()));
        return "redirect:" + ViewName.RESET_PASSWORD_EMAIL_SENT;
    }

    @RequestMapping(value = ViewName.RESET_PASSWORD_EMAIL_SENT, method = RequestMethod.GET)
    public String showResetPasswordEmailSent() {
        return ViewName.RESET_PASSWORD_EMAIL_SENT;
    }


    @RequestMapping(value = ViewName.CHANGE_PASSWORD, method = RequestMethod.GET)
    public ModelAndView showChangePassword(Locale locale, @RequestParam("id") Long id, @RequestParam("token") String token) {

        String validationErrorMessage = userSecurityService.validatePasswordResetToken(id, token);
        ModelAndView mav = new ModelAndView();
        if (validationErrorMessage == null) {
            PasswordResetToken passwordResetToken = userSecurityService.findByToken(token);
            giveUserChangePasswordPrivilege(passwordResetToken);
            mav.setViewName("redirect:" + ViewName.UPDATE_PASSWORD + "?lang=" + locale.getLanguage());
            return mav;
        }
        // model.addAttribute("message", messageSource.getMessage("auth.message." + result, null, locale));

        mav.setViewName("redirect:" + ViewName.RESET_PASSWORD_TOKEN_INVALID + "?lang=" + locale.getLanguage() + "&errorMessage=" + validationErrorMessage);
        return mav;
    }

    @RequestMapping(value = ViewName.RESET_PASSWORD_TOKEN_INVALID, method = RequestMethod.GET)
    public ModelAndView showResetPasswordTokenInvalid(@RequestParam("errorMessage") String errorMessage){
        ModelAndView mav = new ModelAndView(ViewName.RESET_PASSWORD_TOKEN_INVALID);
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    @RequestMapping(value = ViewName.UPDATE_PASSWORD, method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView updatePassword(@ModelAttribute("woodulikeUser") WoodulikeUser woodulikeUser) {
        ModelAndView mav = new ModelAndView(ViewName.UPDATE_PASSWORD);

        mav.addObject("tmpWoodulikeUser", new WoodulikeUser());

        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        return mav;
    }

    @RequestMapping(value = ViewName.UPDATE_PASSWORD_PROCESS, method = RequestMethod.POST)
    public String updatePasswordProcess(@ModelAttribute("tmpWoodulikeUser") @Validated(IResetPasswordValidationGroup.class) WoodulikeUser tmpWoodulikeUser, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ViewName.UPDATE_PASSWORD;
        }

        ModelAndView mav = new ModelAndView();
        TmpUserWrapper user = (TmpUserWrapper)SecurityContextHolder.getContext().getAuthentication();
        userSecurityService.changePassword(user.getWoodulikeUser(), tmpWoodulikeUser.getPassword());

        return "redirect:" + ViewName.LOGIN;
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

    private void giveUserChangePasswordPrivilege(PasswordResetToken passwordResetToken) {
        final WoodulikeUser woodulikeUser = passwordResetToken.getWoodulikeUser();

        final Authentication auth = new TmpUserWrapper("changePasswordUser", Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")), woodulikeUser);
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
