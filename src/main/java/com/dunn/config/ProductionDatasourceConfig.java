package com.dunn.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Component
@Profile("production")
public class ProductionDatasourceConfig {

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("etoblatmlgqsea");
        dataSource.setPassword("ff6cf781c700a9b4c6640ebb6744ba7e7ad14d3c3b02cee055844389b5a685b8");
        dataSource.setUrl("jdbc:postgres://etoblatmlgqsea:ff6cf781c700a9b4c6640ebb6744ba7e7ad14d3c3b02cee055844389b5a685b8@ec2-23-21-171-249.compute-1.amazonaws.com:5432/dfhie9hmsobu6b\n");
        return dataSource;
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "create-drop");
                setProperty("hibernate.dialect",
                        "org.hibernate.dialiect.PostgreSQL95Dialect");
                setProperty("hibernate.globally_quoted_identifiers",
                        "true");
            }
        };
    }

}
