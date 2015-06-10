package com.quifers.properties;

import java.util.Properties;

public class DBPersisterProperties {

    private final Properties properties;

    public DBPersisterProperties(Properties properties) {
        this.properties = properties;
    }

    public String getDriverClass() {
        return properties.getProperty("hibernate.connection.driver_class");
    }

    public String getUrl() {
        return properties.getProperty("hibernate.connection.url");
    }

    public String getUserName() {
        return properties.getProperty("hibernate.connection.username");
    }

    public String getPassword() {
        return properties.getProperty("hibernate.connection.password");
    }


}
