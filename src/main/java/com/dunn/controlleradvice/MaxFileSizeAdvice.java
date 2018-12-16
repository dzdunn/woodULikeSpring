package com.dunn.controlleradvice;

import com.dunn.controller.uipaths.views.ViewName;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ControllerAdvice
public class MaxFileSizeAdvice {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded(MaxUploadSizeExceededException exc, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, WebRequest webRequest, HttpSession session) throws IOException {



            httpServletResponse.sendRedirect(ViewName.HOME);

        ModelAndView m = new ModelAndView();

        return m;
    }


    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView handleNP(NullPointerException exc, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(ViewName.LOGIN);
        mav.addObject("message", "TOO BIG!");
        return mav;
    }
}
