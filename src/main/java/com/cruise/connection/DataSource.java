package com.cruise.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private final HikariConfig config;
    private final HikariDataSource ds;

    public DataSource() {
        String jdbcUrl = "jdbcUrl";
        String jdbcUser = "user";
        String jdbcPassword = "password";
        String jdbcDriver = "driverClassName";
        config = new HikariConfig();
        config.setJdbcUrl(DaoProperties.getProperty(jdbcUrl));
        config.setUsername(DaoProperties.getProperty(jdbcUser));
        config.setPassword(DaoProperties.getProperty(jdbcPassword));
        config.setDriverClassName(DaoProperties.getProperty(jdbcDriver));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
