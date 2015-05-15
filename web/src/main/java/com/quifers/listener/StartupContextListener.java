package com.quifers.listener;

import com.quifers.db.DatabaseHelper;
import com.quifers.properties.Environment;
import com.quifers.properties.QuifersProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import static com.quifers.properties.PropertiesLoader.loadProperties;

public class StartupContextListener implements ServletContextListener {

    public static final String DATABASE_HELPER = "DATABASE_HELPER";
    public static final String ORDER_ID_COUNTER = "ORDER_ID_COUNTER";

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            LOGGER.info("Starting quifers webapp...");
            ServletContext servletContext = servletContextEvent.getServletContext();
            Environment environment = getEnvironment(servletContext);
            QuifersProperties quifersProperties = loadProperties(environment);
            setUpDatabaseConnection(quifersProperties, servletContext);
            setUpOrderIdCounter(servletContext);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Environment getEnvironment(ServletContext servletContext) {
        String env = servletContext.getInitParameter("env");
        if (env == null) {
            throw new IllegalArgumentException("No environment specified for running application.Available environments " + Environment.values().toString());
        }
        Environment environment;
        try {
            environment = Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("No such Environment [" + env + "] present in Environment enum.", exception);
        }
        return environment;
    }

    private void setUpDatabaseConnection(QuifersProperties quifersProperties, ServletContext servletContext) throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(quifersProperties.getDbUrl());
        servletContext.setAttribute(DATABASE_HELPER, databaseHelper);
    }

    public void setUpOrderIdCounter(ServletContext servletContext) {
        servletContext.setAttribute(ORDER_ID_COUNTER, new AtomicLong(1L));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Stopping quifers webapp...");
    }

}
