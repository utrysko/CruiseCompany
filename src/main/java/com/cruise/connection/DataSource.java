package com.cruise.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private HikariConfig config;
    private final String JDBC_URL = "jdbcUrl";
    private final String JDBC_DRIVER = "driverClassName";
    private final String JDBC_USER = "user";
    private final String JDBC_PASSWORD = "password";
    private HikariDataSource ds;

    public DataSource() {

        config = new HikariConfig();
        config.setJdbcUrl(DaoProperties.getProperty(JDBC_URL));
        config.setUsername(DaoProperties.getProperty(JDBC_USER));
        config.setPassword(DaoProperties.getProperty(JDBC_PASSWORD));
        config.setDriverClassName(DaoProperties.getProperty(JDBC_DRIVER));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
