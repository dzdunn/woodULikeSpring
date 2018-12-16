package com.dunn.config.webapp;

import com.dunn.config.db.PersistenceJPAConfig;
import com.dunn.config.properties.WoodulikeProperties;
import com.dunn.config.schduler.SchedulerConfig;
import com.dunn.config.security.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Component
public class WebAppInitialiser extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration){
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/", 500000000, 500000000, 0);
        registration.setMultipartConfig(multipartConfigElement);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

////        ResourceBundle resourceBundle = ResourceBundle.getBundle("classpath:configuration/storage.properties");
////        String maxFileSize = resourceBundle.getViewName("storage.maxfilesize");
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
        return new String[]{ "", "/"};
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
