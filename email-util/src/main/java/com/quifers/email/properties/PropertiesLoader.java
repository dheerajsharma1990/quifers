package com.quifers.email.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    public EmailUtilProperties getEmailUtilProperties(Environment environment) throws IOException {
        LOGGER.info("Loading email util properties for environment: [{}]", environment);
        String pathToProperties = "properties" + File.separator + environment.name().toLowerCase() + File.separator + "email-util.properties";
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(pathToProperties);
        Properties properties = new Properties();
        properties.load(inputStream);
        return new EmailUtilProperties(properties);
    }
}
