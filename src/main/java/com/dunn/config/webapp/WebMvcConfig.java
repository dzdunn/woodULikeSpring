package com.dunn.config.webapp;

import com.dunn.controller.uipaths.resources.ResourceProperties;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.dunn"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(ResourceProperties.STATIC_PROPERTIES.getResourceHandler().getResourceHandlerString())
		.addResourceLocations(ResourceProperties.STATIC_PROPERTIES.getResourceLocations())
		.setCacheControl(
				CacheControl.maxAge(30L, TimeUnit.MILLISECONDS).cachePublic()) //Change back to minutes
		.resourceChain(true)
		.addResolver(new WebJarsResourceResolver());

		registry.addResourceHandler(ResourceProperties.IMG_PROPERTIES.getResourceHandler().getResourceHandlerString()).addResourceLocations(ResourceProperties.IMG_PROPERTIES.getResourceLocations());


		registry.addResourceHandler(ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourceHandler().getResourceHandlerString()).addResourceLocations(ResourceProperties.CREATE_WOOD_PROJECT_TEMP_PROPERTIES.getResourceLocationsAsUriFormattedString());

		registry.addResourceHandler(ResourceProperties.WOOD_PROJECT_IMAGE_PROPERTIES.getResourceHandler().getResourceHandlerString()).addResourceLocations(ResourceProperties.WOOD_PROJECT_IMAGE_PROPERTIES.getResourceLocationsAsUriFormattedString());

	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		//viewResolver.setExposedContextBeanNames("viewName");

		return viewResolver;
	}

	@Bean
	public File fileSystemRoot(){
		try {
			return Files.createTempDirectory("").toFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public FileSystemResourceLoader fileSystemResourceLoader() {
		FileSystemResourceLoader loader = new FileSystemResourceLoader();
		return loader;

	}

//	@Bean
//	public CommonsMultipartResolver multipartResolver() {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(100000);
//		multipartResolver.setServletContext(servletContext);
//		return multipartResolver;
//	}

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}


	@Bean
	public JavaMailSenderImpl javaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("woodulike.organisation");
		mailSender.setPassword("jifvswucourixgau");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public ValidatorFactory validatorFactory(){
		ValidatorFactory validatorFactory = Validation
				.byProvider(HibernateValidator.class).configure()
				.constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
				.messageInterpolator(new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource())))
				.buildValidatorFactory();
		return validatorFactory;
	}

	@Override
	public Validator getValidator() {
		return new SpringValidatorAdapter(validatorFactory().getValidator());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public HandlerExceptionResolver handlerExceptionResolver(){
//		ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
//		resolver.setApplicationContext(webApplicationContext);
//		resolver.setContentNegotiationManager();
//		return resolver;
//	}
}
