package com.quifers.properties;

import java.util.Properties;

public class QuifersProperties {

    private static final String DRIVER_CLASS = "DRIVER_CLASS";
    private static final String QUIFERSDB_URL = "QUIFERSDB_URL";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";


    private final Properties properties;

    public QuifersProperties(Properties properties) {
        this.properties = properties;
    }


    public String getDriverClass() {
        return properties.getProperty(DRIVER_CLASS);
    }

    public String getDbUrl() {
        return properties.getProperty(QUIFERSDB_URL);
    }
    public String getUsername() {
        return properties.getProperty(USER);
    }
    public String getPassword() {
        return properties.getProperty(PASSWORD);
    }
}
