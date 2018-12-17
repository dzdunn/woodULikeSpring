package com.dunn.config.db;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;


@Component
@Profile("dev")
public class DevDatasourceConfig implements WoodulikeDataSource{

    @Value("${dev.datasource.password}")
    private String password;

    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword(password);
        dataSource.setUrl("jdbc:postgresql://localhost:5432/woodulike-local");
        return dataSource;
    }

    public Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "create-drop");
                setProperty("hibernate.dialect",
                        "org.hibernate.dialect.PostgreSQL95Dialect");
                setProperty("hibernate.globally_quoted_identifiers",
                        "true");
            }
        };
    }


}
