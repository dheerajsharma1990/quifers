package com.quifers.email;

import com.quifers.email.servlet.AccessCodeRequestServlet;
import com.quifers.email.servlet.AccessTokenRequestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {

    public static Server runJettyServer(int port) throws Exception {
        Server server = new Server(port);
        String webappDirLocation = "src/main/webapp/";

        WebAppContext contextHandler = new WebAppContext();

        contextHandler.setResourceBase(webappDirLocation);
        contextHandler.setParentLoaderPriority(true);
        contextHandler.addServlet(new ServletHolder(new AccessCodeRequestServlet()), "/accessCode");
        contextHandler.addServlet(new ServletHolder(new AccessTokenRequestServlet()), "/callback");
        server.setHandler(contextHandler);

        server.start();

        return server;
    }

    public static void main(String[] args) throws Exception {
        Server server = runJettyServer(8008);
        server.join();
    }

}
