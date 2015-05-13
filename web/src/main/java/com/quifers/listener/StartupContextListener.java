package com.quifers.listener;

import com.quifers.db.DatabaseHelper;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.SQLException;

public class StartupContextListener implements ServletContextListener {

    public static final String DATABASE_HELPER = "DATABASE_HELPER";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
            setUpDatabaseConnection(quifersProperties, servletContext);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpDatabaseConnection(QuifersProperties quifersProperties, ServletContext servletContext) throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(quifersProperties.getDbUrl());
        servletContext.setAttribute(DATABASE_HELPER, databaseHelper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
