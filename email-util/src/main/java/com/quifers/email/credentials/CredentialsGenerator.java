package com.quifers.email.credentials;

import com.quifers.email.credentials.servlet.AccessCodeRequestServlet;
import com.quifers.email.credentials.servlet.AccessTokenRequestServlet;
import com.quifers.email.credentials.servlet.StartupContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CredentialsGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialsGenerator.class);

    public static Server runJettyServer(int port) throws Exception {

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setInitParameter("env", "LOCAL");
        context.addEventListener(new StartupContextListener());
        context.addServlet(new ServletHolder(new AccessCodeRequestServlet()), "/accessCode");
        context.addServlet(new ServletHolder(new AccessTokenRequestServlet()), "/callback");
        server.setHandler(context);

        server.start();
        LOGGER.info("Web App Started...");
        LOGGER.info("Open http://<machine-ip>:8008/accessCode to generate credentials..");
        return server;
    }

    public static void main(String[] args) throws Exception {
        Server server = runJettyServer(8008);
        server.join();
    }

}
