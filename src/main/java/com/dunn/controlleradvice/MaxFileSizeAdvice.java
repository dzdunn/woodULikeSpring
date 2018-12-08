package com.dunn.controlleradvice;

import com.dunn.controller.uipaths.views.ViewName;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MaxFileSizeAdvice {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded(SizeLimitExceededException exc,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
            ModelAndView mav = new ModelAndView(ViewName.HOME);
            mav.addObject("message", "TOO BIG!");
            return mav;
    }
}
