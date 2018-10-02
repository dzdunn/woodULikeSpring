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
	@PropertySource("classpath:configuration/UI.properties"),
		@PropertySource(value = "classpath:configuration/local.properties", ignoreResourceNotFound = true)
})
public class WoodulikeProperties {

	@Bean
	public PropertiesFactoryBean uiProperties() {
		PropertiesFactoryBean uiProperties = new PropertiesFactoryBean();
		uiProperties.setLocation(new ClassPathResource("configuration/UI.properties"));
		return uiProperties;
	}

	@Bean
	public PropertiesFactoryBean databaseProperties() {
		PropertiesFactoryBean databaseProperties = new PropertiesFactoryBean();
		databaseProperties.setLocation(new ClassPathResource("configuration/database.properties"));
		return databaseProperties;
	}

}
