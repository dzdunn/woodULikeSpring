package com.dunn.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitialiser implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebMvcConfig.class);
		rootContext.setServletContext(servletContext);

		servletContext.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic dispatcher = servletContext
		          .addServlet("dispatcher", new DispatcherServlet(rootContext));
		         
		        dispatcher.setLoadOnStartup(1);
		        dispatcher.addMapping("/");



	}

}
