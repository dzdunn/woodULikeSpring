package com.dunn.config.security.filter;

import com.dunn.config.security.filter.httpwrappers.PostToGetRequestWrapper;
import com.dunn.config.session.SessionNavigation;
import com.dunn.controller.mywoodprojects.FileUploadController;
import com.dunn.controlleradvice.MaxFileSizeAdvice;
import com.dunn.dto.ui.WoodProjectDTO;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Component
//@WebFilter(filterName = "imageUploadFilter", urlPatterns = "/*")
public class ImageUploadFilter extends DelegatingFilterProxy {

    @Autowired
    private SessionNavigation sessionNavigation;

    @Value("${storage.maxfilesize}")
    private long maxFileSize;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MaxFileSizeAdvice maxFileSizeAdvice;

    @Autowired
    private FileUploadController fileUploadController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().contains("multipart/form-data")) {
            Collection<Part> parts = httpServletRequest.getParts();
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            for (Part part : parts) {
                //file size check
                if(part.getSize() > maxFileSize){
                    httpServletRequest.setAttribute("errorMessage", messageSource.getMessage("upload.exceededmaxfilesize", new Object[]{part.getSize(), maxFileSize}, LocaleContextHolder.getLocale()));
                    httpServletRequest.getRequestDispatcher("/testHandle").forward(request, response);
                    return;

                  //  PostToGetRequestWrapper wrapper = new PostToGetRequestWrapper(httpServletRequest);

//                    wrapper.setSession(httpServletRequest.getSession());
//                    wrapper.setParts(httpServletRequest.getParts());

//                    httpServletRequest.getRequestDispatcher(sessionNavigation.getLastRequest()).forward(request, response);


                   // throw new MaxUploadSizeExceededException(maxFileSize);
                   // ((HttpServletResponse) response).sendRedirect(sessionNavigation.getLastRequest());
                   // return;
                }
                if (part.getSubmittedFileName() != null && !isImageSuffix(part.getSubmittedFileName()) && part.getContentType() != null && !isImageContentType(part.getContentType())) {

                    ((HttpServletResponse) response).sendRedirect(sessionNavigation.getLastRequest());
                    return;
                }


            }
        }

        chain.doFilter(request, response);
    }

    private final static String[] IMG_CONTENT_TYPES = {
            "image/jpeg",
            "image/gif",
            "image/png",
            "image/svg+xml"
    };

    private final static String[] IMG_SUFFIXES = {
            ".jpg",
            ".jpeg",
            ".gif",
            ".png",
            ".svg"
    };

    private boolean isImageContentType(String contentType) {
        return Arrays.stream(IMG_CONTENT_TYPES).anyMatch(type -> type.equals(contentType));
    }

    private boolean isImageSuffix(String filename) {
        return Arrays.stream(IMG_SUFFIXES).anyMatch(suffix -> filename.endsWith(suffix));
    }


}
