package com.dunn.config.webapp;

import com.dunn.config.security.filter.ImageUploadFilter;
import com.dunn.config.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitialiser implements WebApplicationInitializer {


	@Override
	public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebMvcConfig.class);
		rootContext.setServletContext(servletContext);

		servletContext.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic dispatcher = servletContext
		          .addServlet("dispatcher", new DispatcherServlet(rootContext));
		         
		        dispatcher.setLoadOnStartup(1);
		        dispatcher.addMapping("/");

		servletContext.addListener(SessionListener.class);
		servletContext.addListener(new RequestContextListener());

		MultipartConfigElement multipartConfig = new MultipartConfigElement(
				"/", 50000000, 50000000, 0);


		dispatcher.setMultipartConfig(multipartConfig);
		servletContext.addFilter("imageUploadFilter", ImageUploadFilter.class);


	}

}
