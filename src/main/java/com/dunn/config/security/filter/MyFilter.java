package com.dunn.config.security.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MyFilter extends GenericFilterBean {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        ServletContext servletContext = filterConfig.getServletContext();
//
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getContentType() != null && httpServletRequest.getContentType().contains("multipart/form-data")) {

            Collection<Part> parts = httpServletRequest.getParts();
            for (Part part : parts) {
                if (part.getName().equals("imageFile")) {

                    System.out.println("Submitted file name: " + part.getSubmittedFileName());
                    System.out.println("Part Name: " + part.getName());
                    System.out.println("Content type: " + part.getContentType());
                    System.out.println("Name: " + part.getName());
                    System.out.println("Size: " + part.getSize());

                    InputStream inputStream = part.getInputStream();

                    System.out.println("*********************");
                    System.out.println(httpServletRequest.getContextPath());
                    System.out.println(httpServletRequest.getRequestURI());
                    System.out.println(httpServletRequest.getHttpServletMapping());
                    System.out.println(httpServletRequest.getPathInfo());
                    System.out.println(httpServletRequest.getRequestURL());
                    System.out.println(httpServletRequest.getServletPath());
                    System.out.println(httpServletRequest.getPathTranslated());
//
//                    ((HttpServletResponse)response).sendRedirect(ViewName.HOME);
//                    return;

                }
            }
        }
        chain.doFilter(request, response);
    }

//    @Override
//    public void destroy() {
//
//    }
}
