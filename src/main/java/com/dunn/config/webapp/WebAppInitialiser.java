package com.dunn.config.webapp;

import com.dunn.config.db.PersistenceJPAConfig;
import com.dunn.config.properties.WoodulikeProperties;
import com.dunn.config.schduler.SchedulerConfig;
import com.dunn.config.security.SecurityConfig;
import com.dunn.config.security.filter.ImageUploadFilter;
import com.dunn.config.session.SessionListener;
import com.dunn.model.woodproject.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import java.util.ResourceBundle;

@Component
public class WebAppInitialiser extends AbstractAnnotationConfigDispatcherServletInitializer {

//    @Value("${storage.maxfilesize}")
//    private long maxFileSize;

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration){
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/", 500000000, 500000000, 0);
        registration.setMultipartConfig(multipartConfigElement);
    }

//    @Override
//    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
//        return dispatcherServlet;
//
//    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

////        ResourceBundle resourceBundle = ResourceBundle.getBundle("classpath:configuration/storage.properties");
////        String maxFileSize = resourceBundle.getString("storage.maxfilesize");
        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(WebMvcConfig.class);
        //rootContext.setServletContext(servletContext);
//
        //servletContext.addListener(new ContextLoaderListener(rootContext));
////
////        ServletRegistration.Dynamic dispatcher = servletContext
////                .addServlet("dispatcher", new DispatcherServlet(rootContext));
////
////        dispatcher.setLoadOnStartup(1);
////        dispatcher.addMapping("/");
//
//        servletContext.addListener(SessionListener.class);
        servletContext.addListener(new RequestContextListener());

        //servletContext.addFilter("imageUploadFilter", ImageUploadFilter.class);
//        servletContext.addListener(MyServletContextListener.class);

//        ServletRegistration.Dynamic fileUploadServlet = servletContext.addServlet(
//                "fileUploadServlet", FileUploadServlet.class);
//        fileUploadServlet.setLoadOnStartup(2);
//        fileUploadServlet.addMapping("/");

////        MultipartConfigElement multipartConfig = new MultipartConfigElement(
////                "/", 500000, 500000, 0);
//
//
////        dispatcher.setMultipartConfig(multipartConfig);
////        servletContext.addFilter("imageUploadFilter", ImageUploadFilter.class);
//
//
    }

//    @Override
//    protected Filter[] getServletFilters(){
//        return new Filter[]{};
//    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebMvcConfig.class, SecurityConfig.class, PersistenceJPAConfig.class, SchedulerConfig.class, WoodulikeProperties.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }
}
