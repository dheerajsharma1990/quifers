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

    public static WebProperties loadProperties(Environment environment) throws IOException {
        LOGGER.info("Loading properties for environment: [{}]", environment);
        String pathToProperties = "properties" + File.separator + environment.name().toLowerCase() + File.separator + "web.properties";
        InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(pathToProperties);
        Properties properties = new Properties();
        properties.load(inputStream);
        return new WebProperties(properties);
    }

}
