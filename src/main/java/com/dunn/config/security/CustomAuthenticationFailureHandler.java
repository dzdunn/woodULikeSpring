package com.dunn.config.security;

import com.dunn.controller.uipaths.views.ViewName;
import com.dunn.model.user.WoodulikeUser;
import com.dunn.validation.login.IWoodulikeUserLoginValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private Validator validator;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        WoodulikeUser woodulikeUser = new WoodulikeUser();
        woodulikeUser.setPassword(request.getParameter("password"));
        woodulikeUser.setUsername(request.getParameter("username"));


        SpringValidatorAdapter springValidatorAdapter2 = (SpringValidatorAdapter) validator;

        BindingResult bindingResult = new BeanPropertyBindingResult(woodulikeUser, "woodulikeUser");

        springValidatorAdapter2.validate(woodulikeUser, bindingResult, IWoodulikeUserLoginValidationGroup.class);

        request.getSession().setAttribute("woodulikeUser", woodulikeUser);
        request.getSession().setAttribute("bindingResult", bindingResult);

        response.sendRedirect(ViewName.LOGIN_FAILURE);
        return;

    }

}
