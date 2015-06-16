package com.quifers.properties;

import java.util.Properties;

public class WebProperties {

    private static final String DRIVER_CLASS = "DRIVER_CLASS";
    private static final String QUIFERSDB_URL = "QUIFERSDB_URL";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";
    private static final String LAST_ORDER_ID_COUNTER = "LAST_ORDER_ID_COUNTER";


    private final Properties properties;

    public WebProperties(Properties properties) {
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
    public long getLastOrderIdCounter() {
        return Long.valueOf(properties.getProperty(LAST_ORDER_ID_COUNTER));
    }
}
