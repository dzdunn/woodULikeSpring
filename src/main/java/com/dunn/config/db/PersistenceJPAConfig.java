package com.dunn.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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

//    @Autowired
//    private DevDatasourceConfig devDatasourceConfig;
//
//    @Autowired
//    private ProductionDatasourceConfig productionDatasourceConfig;

    @Autowired
    private WoodulikeDataSource woodulikeDataSource;

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
        return woodulikeDataSource.dataSource();
//        switch (env.getProperty("spring.profiles.active")) {
//            case "dev":
//                return devDatasourceConfig.dataSource();
//            case "production":
//                return productionDatasourceConfig.dataSource();
//            default:
//                throw new IllegalArgumentException("No datasource set for current profile.");
//        }
    }

    private Properties properties() {
        return woodulikeDataSource.hibernateProperties();
//        switch (env.getProperty("spring.profiles.active")) {
//            case "dev":
//                return globalHibernateProperties(devDatasourceConfig.hibernateProperties());
//            case "production":
//                return globalHibernateProperties(productionDatasourceConfig.hibernateProperties());
//            default:
//                throw new IllegalArgumentException("No datasource set for current profile.");
//        }
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
