package com.quifers;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class Environment {

    private static final Environment[] allEnvironments = new Environment[]{
            new Environment("LOCAL"),
            new Environment("DEV")};

    private String name;

    private Environment(String name) {
        this.name = name;
    }

    public String getPropertiesFilePath(String fileName) {
        return "properties" + File.separator + name.toLowerCase() + File.separator + fileName;
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
