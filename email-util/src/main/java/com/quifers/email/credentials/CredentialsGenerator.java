package com.quifers.email.credentials;

import com.quifers.Environment;
import com.quifers.email.credentials.servlet.AccessCodeRequestServlet;
import com.quifers.email.credentials.servlet.AccessTokenRequestServlet;
import com.quifers.email.credentials.servlet.StartupContextListener;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class CredentialsGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialsGenerator.class);

    public static void main(String[] args) throws Exception {
        loadLog4jProperties(Environment.getEnvironment(System.getProperty("env")));
        Server server = runJettyServer(8008);
        server.join();
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = CredentialsGenerator.class.getClassLoader().getResourceAsStream(environment.getPropertiesFilePath("log4j.properties"));
        PropertyConfigurator.configure(inputStream);
    }

    private static Server runJettyServer(int port) throws Exception {

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setInitParameter("env", "dev");
        context.addEventListener(new StartupContextListener());
        context.addServlet(new ServletHolder(new AccessCodeRequestServlet()), "/accessCode");
        context.addServlet(new ServletHolder(new AccessTokenRequestServlet()), "/callback");
        server.setHandler(context);

        server.start();
        LOGGER.info("Web App Started...");
        LOGGER.info("Open http://<machine-ip>:8008/accessCode to generate credentials..");
        return server;
    }

}
