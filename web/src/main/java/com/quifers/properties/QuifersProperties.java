package com.quifers.properties;

public class QuifersProperties {

    private String driverClass;
    private String url;

    public QuifersProperties(String driverClass, String url) {
        this.driverClass = driverClass;
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getDbUrl() {
        return url;
    }
}
