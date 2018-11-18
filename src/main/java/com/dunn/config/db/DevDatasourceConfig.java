package com.dunn.config.db;


import com.dunn.dao.user.UserService;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;


@Component
@Profile("dev")
public class DevDatasourceConfig {

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/woodulike-local");
        return dataSource;
    }

    Properties hibernateProperties() {
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
