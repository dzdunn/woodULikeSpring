package com.dunn.config.db;

import javax.sql.DataSource;
import java.util.Properties;

public interface WoodulikeDataSource {

    DataSource dataSource();

    Properties hibernateProperties();

}
