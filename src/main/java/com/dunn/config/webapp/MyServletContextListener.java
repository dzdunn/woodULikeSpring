package com.dunn.config.webapp;

import com.dunn.config.properties.WoodulikeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyServletContextListener.class);

    private Properties properties;

    private String activeProfile;

    private final static String[] profilePropertyLocations = {"configuration/local.properties", "configuration/database.properties"};

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        resolveActiveProfile();
        LOGGER.info("Profile is: " + activeProfile);
        LOGGER.info("Starting up!");
        System.setProperty("spring.profiles.active", activeProfile);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Shutting down!");
    }

    private String resolveActiveProfile(){
        int cnt = 0;
        properties = new Properties();
        while(activeProfile == null && cnt < profilePropertyLocations.length){
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(profilePropertyLocations[cnt])) {
                properties.load(inputStream);
                activeProfile = properties.getProperty("spring.profiles.active");
            } catch (FileNotFoundException e) {
                continue;
            } catch (IOException e) {
                continue;
            }
        }
        if (activeProfile == null) {throw new IllegalArgumentException("No profile was set");}
        return activeProfile;
    }

}
