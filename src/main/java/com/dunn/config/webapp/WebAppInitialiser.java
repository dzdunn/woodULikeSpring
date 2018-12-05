package com.dunn.config.webapp;

import com.dunn.config.session.SessionListener;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

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

//		FilterRegistration.Dynamic filter = servletContext.addFilter("myFilter", MyFilter.class);
//		filter.addMappingForUrlPatterns(null, true, "/*");

		MultipartConfigElement multipartConfig = new MultipartConfigElement(
				"/", 50000000, 50000000, 0);

		dispatcher.setMultipartConfig(multipartConfig);



	}

}
