package com.dunn.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasource());
        sessionFactory.setHibernateProperties(properties());
        sessionFactory.setPackagesToScan(new String[] {"com.dunn.model"});
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource datasource(){
        switch(env.getProperty("spring.profiles.active")){
            case "dev":
                return new DevDatasourceConfig().dataSource();
            case "production":
                return new ProductionDatasourceConfig().dataSource();
            default:
                throw new IllegalArgumentException("No datasource set for current profile.");
        }
    }

   private Properties properties(){
       switch(env.getProperty("spring.profiles.active")){
           case "dev":
               return new DevDatasourceConfig().hibernateProperties();
           case "production":
               return new ProductionDatasourceConfig().hibernateProperties();
           default:
               throw new IllegalArgumentException("No datasource set for current profile.");
       }
   }
}
