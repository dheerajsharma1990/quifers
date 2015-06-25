package com.quifers.properties;

import java.util.Properties;

public class DBPersisterProperties {

    private final Properties properties;

    public DBPersisterProperties(Properties properties) {
        this.properties = properties;
    }

    public String getDriverClass() {
        return properties.getProperty("DRIVER_CLASS");
    }

    public String getUrl() {
        return properties.getProperty("QUIFERSDB_URL");
    }

    public String getUserName() {
        return properties.getProperty("USER");
    }

    public String getPassword() {
        return properties.getProperty("PASSWORD");
    }


}
