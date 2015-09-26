package com.quifers;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);

    private static final Environment[] allEnvironments = new Environment[]{
            new Environment("LOCAL"),
            new Environment("DEV")};

    private String name;

    private Environment(String name) {
        this.name = name;
    }

    public Properties loadProperties(String fileName) throws IOException {
        loadLog4jProperties();
        return getProperties(fileName);
    }

    private Properties getProperties(String fileName) throws IOException {
        LOGGER.info("Loading properties from {}", fileName);
        Properties properties = new Properties();
        properties.load(getInputStreamForFile(fileName));
        return properties;
    }

    public void loadLog4jProperties() {
        PropertyConfigurator.configure(getInputStreamForFile("log4j.properties"));
    }

    private InputStream getInputStreamForFile(String fileName) {
        return Environment.class.getClassLoader().getResourceAsStream("properties" + File.separator + name.toLowerCase() + File.separator + fileName);
    }

    public static Environment getEnvironment(String environment) {
        if (!StringUtils.isEmpty(environment)) {
            for (Environment env : allEnvironments) {
                if (env.name.toUpperCase().equals(environment.toUpperCase())) {
                    return env;
                }
            }
        }
        throw new IllegalArgumentException("No valid environment exists with argument [" + environment + "].");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Environment that = (Environment) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                '}';
    }
}
