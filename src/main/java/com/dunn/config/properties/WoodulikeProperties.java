package com.dunn.config.properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySources({
        @PropertySource("classpath:configuration/database.properties"),
        @PropertySource("classpath:configuration/mailsender.properties"),
        @PropertySource("classpath:configuration/storage.properties"),
        @PropertySource("classpath:i18n/messages.properties"),
        @PropertySource(value = "classpath:configuration/local.properties", ignoreResourceNotFound = true)
})
public class WoodulikeProperties {


//    @Bean
//    public PropertiesFactoryBean databaseProperties() {
//        PropertiesFactoryBean databaseProperties = new PropertiesFactoryBean();
//        databaseProperties.setLocation(new ClassPathResource("configuration/database.properties"));
//        return databaseProperties;
//    }
//
    @Bean(name = "storageServiceProperties")
    public PropertiesFactoryBean storageServiceProperties(){
        PropertiesFactoryBean storageServiceProperties = new PropertiesFactoryBean();
        storageServiceProperties.setLocation(new ClassPathResource("configuration/storage.properties"));
        return storageServiceProperties;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PropertiesFactoryBean localProperties(){
        PropertiesFactoryBean localProperties = new PropertiesFactoryBean();
        localProperties.setLocation(new ClassPathResource("configuration/local.properties"));
        return localProperties;
    }

}
