package com.dunn.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySources({
	@PropertySource("classpath:configuration/database.properties"),
	@PropertySource("classpath:configuration/UI.properties")
})
public class Properties {

	@Bean
	public PropertiesFactoryBean uiProperties() {
		PropertiesFactoryBean uiProperties = new PropertiesFactoryBean();
		uiProperties.setLocation(new ClassPathResource("configuration/UI.properties"));
		return uiProperties;
	}

}
