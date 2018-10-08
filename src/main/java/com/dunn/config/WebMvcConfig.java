package com.dunn.config;

import com.dunn.controller.path.ViewName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.TimeUnit;
@Configuration
@EnableWebMvc
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.dunn"})
@EnableTransactionManagement
public class WebMvcConfig implements WebMvcConfigurer{


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
		.addResourceLocations("/resources/", "/webjars/")
		.setCacheControl(
				CacheControl.maxAge(30L, TimeUnit.MILLISECONDS).cachePublic()) //Change back to minutes
		.resourceChain(true)
		.addResolver(new WebJarsResourceResolver());

		registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");

	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		//viewResolver.setExposedContextBeanNames("viewName");
		return viewResolver;
	}

//	@Bean
//	public ViewName viewName() {
//		return ViewName.getInstance();
//	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000);
		return multipartResolver;
	}


}
