package com.quifers.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String DRIVER_CLASS = "DRIVER_CLASS";
    private static final String QUIFERSDB_URL = "QUIFERSDB_URL";

    public static QuifersProperties loadProperties(Environment environment) throws IOException {
        String pathToProperties = "properties" + File.separator + environment.name().toLowerCase() + File.separator + "quifers.properties";
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(pathToProperties);
        Properties properties = new Properties();
        properties.load(inputStream);
        return new QuifersProperties(properties.getProperty(DRIVER_CLASS), properties.getProperty(QUIFERSDB_URL));
    }

}
