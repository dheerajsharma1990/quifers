package com.quifers.properties;

import com.quifers.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    public static DBPersisterProperties loadDbPersisterProperties(Environment environment) throws IOException {
        LOGGER.info("Loading db persister properties for environment: [{}]", environment);
        String pathToProperties = getPropertyFileLocation(environment);
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(pathToProperties);
        Properties properties = new Properties();
        properties.load(inputStream);
        return new DBPersisterProperties(properties);
    }

    private static String getPropertyFileLocation(Environment environment) {
        return "properties" + File.separator + environment.name().toLowerCase() + File.separator + "hibernate.properties";
    }

}
