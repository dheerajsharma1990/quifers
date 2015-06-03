package com.quifers.email.credentials.servlet;

import com.quifers.Environment;
import com.quifers.email.builders.AccessCodeRequestBuilder;
import com.quifers.email.builders.AccessTokenRequestBuilder;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.HttpRequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

import static com.quifers.email.properties.PropertiesLoader.loadEmailUtilProperties;

public class StartupContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupContextListener.class);

    public static final String EMAIL_UTIL_PROPERTIES = "EMAIL_UTIL_PROPERTIES";
    public static final String ACCESS_CODE_REQUEST_BUILDER = "ACCESS_CODE_REQUEST_BUILDER";
    public static final String ACCESS_TOKEN_REQUEST_BUILDER = "ACCESS_TOKEN_REQUEST_BUILDER";
    public static final String HTTP_REQUEST_SENDER = "HTTP_REQUEST_SENDER";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        initialiseProperties(servletContext);

        servletContext.setAttribute(ACCESS_CODE_REQUEST_BUILDER, new AccessCodeRequestBuilder());
        servletContext.setAttribute(ACCESS_TOKEN_REQUEST_BUILDER, new AccessTokenRequestBuilder());
        servletContext.setAttribute(HTTP_REQUEST_SENDER, new HttpRequestSender());
    }

    private void initialiseProperties(ServletContext servletContext) {
        try {
            Environment environment = Environment.valueOf(servletContext.getInitParameter("env"));
            EmailUtilProperties emailUtilProperties = loadEmailUtilProperties(environment);
            servletContext.setAttribute(EMAIL_UTIL_PROPERTIES, emailUtilProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Shutting down...");
    }
}
