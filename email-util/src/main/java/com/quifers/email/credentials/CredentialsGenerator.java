package com.quifers.email.credentials;

import com.quifers.email.credentials.servlet.AccessCodeRequestServlet;
import com.quifers.email.credentials.servlet.AccessTokenRequestServlet;
import com.quifers.email.credentials.servlet.StartupContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CredentialsGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialsGenerator.class);

    public static Server runJettyServer(int port) throws Exception {
        Server server = new Server(port);
        String webappDirLocation = "src/main/webapp/";

        WebAppContext contextHandler = new WebAppContext();

        contextHandler.setResourceBase(webappDirLocation);
        contextHandler.setParentLoaderPriority(true);
        contextHandler.setInitParameter("env", "LOCAL");
        contextHandler.addEventListener(new StartupContextListener());
        contextHandler.addServlet(new ServletHolder(new AccessCodeRequestServlet()), "/accessCode");
        contextHandler.addServlet(new ServletHolder(new AccessTokenRequestServlet()), "/callback");
        server.setHandler(contextHandler);

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