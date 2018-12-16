package com.dunn.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Validator validator;

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasource());
        sessionFactory.setHibernateProperties(properties());
        sessionFactory.setPackagesToScan(new String[]{"com.dunn.model"});

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource datasource() {
        switch (env.getProperty("spring.profiles.active")) {
            case "dev":
                return new DevDatasourceConfig().dataSource();
            case "production":
                return new ProductionDatasourceConfig().dataSource();
            default:
                throw new IllegalArgumentException("No datasource set for current profile.");
        }
    }

    private Properties properties() {
        switch (env.getProperty("spring.profiles.active")) {
            case "dev":
                return globalHibernateProperties(new DevDatasourceConfig().hibernateProperties());
            case "production":
                return globalHibernateProperties(new ProductionDatasourceConfig().hibernateProperties());
            default:
                throw new IllegalArgumentException("No datasource set for current profile.");
        }
    }

    private Properties globalHibernateProperties(Properties localProperties) {
        localProperties.putAll(
                new Properties() {
                    {
                        put("javax.persistence.validation.factory", validator);
                    }
                }
        );
        return localProperties;
    }



//    @Bean
//    public PostgresqlDAO postgresqlDAO(){
//        PostgresqlDAO postgresqlDAO = new PostgresqlDAO();
//        postgresqlDAO.setSessionFactory(sessionFactory().getObject());
//        return postgresqlDAO;
//    }
}
