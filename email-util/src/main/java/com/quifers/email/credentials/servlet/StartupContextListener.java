package com.quifers.email.credentials.servlet;

import com.quifers.email.credentials.builders.AccessCodeRequestBuilder;
import com.quifers.email.credentials.builders.AccessTokenRequestBuilder;
import com.quifers.email.util.HttpRequestSender;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupContextListener implements ServletContextListener {

    public static final String ACCESS_CODE_REQUEST_BUILDER = "ACCESS_CODE_REQUEST_BUILDER";
    public static final String ACCESS_TOKEN_REQUEST_BUILDER = "ACCESS_TOKEN_REQUEST_BUILDER";
    public static final String HTTP_REQUEST_SENDER = "HTTP_REQUEST_SENDER";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(ACCESS_CODE_REQUEST_BUILDER, new AccessCodeRequestBuilder());
        servletContext.setAttribute(ACCESS_TOKEN_REQUEST_BUILDER, new AccessTokenRequestBuilder());
        servletContext.setAttribute(HTTP_REQUEST_SENDER, new HttpRequestSender());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
