package com.cruise.connection;

import com.cruise.exceptions.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoProperties {

    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
        if (propertiesFile == null) {
            throw new DAOException("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            throw new DAOException("Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }
    }
    public static String getProperty(String key){
        return PROPERTIES.getProperty(key);
    }
}
