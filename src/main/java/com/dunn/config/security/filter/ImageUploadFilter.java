package com.dunn.config.security.filter;

import com.dunn.config.session.SessionNavigation;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().contains("multipart/form-data")) {

            Collection<Part> parts = httpServletRequest.getParts();

            for (Part part : parts) {
                if(part.getSize() > maxFileSize){
                    throw new MaxUploadSizeExceededException(maxFileSize);
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
