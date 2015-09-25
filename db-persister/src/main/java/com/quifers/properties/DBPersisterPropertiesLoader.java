package com.quifers.properties;

import com.quifers.Environment;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPersisterPropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBPersisterPropertiesLoader.class);

    public static DBPersisterProperties loadDbPersisterProperties(Environment environment) throws IOException {
        loadLog4jProperties(environment);
        return getDbPersisterProperties(environment);
    }

    private static DBPersisterProperties getDbPersisterProperties(Environment environment) throws IOException {
        LOGGER.info("Loading db persister properties for {}", environment);
        String pathToProperties = environment.getPropertiesFilePath("db-persister.properties");
        InputStream inputStream = DBPersisterPropertiesLoader.class.getClassLoader().getResourceAsStream(pathToProperties);
        Properties properties = new Properties();
        properties.load(inputStream);
        return new DBPersisterProperties(properties);
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = DBPersisterPropertiesLoader.class.getClassLoader().getResourceAsStream(environment.getPropertiesFilePath("log4j.properties"));
        PropertyConfigurator.configure(inputStream);
    }

}
