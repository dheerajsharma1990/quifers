package com.quifers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRunner {

    public static Server runJettyServer(int port) throws Exception {
        Server server = new Server(port);
        String webappDirLocation = "src/main/webapp/";

        WebAppContext contextHandler = new WebAppContext();

        contextHandler.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        contextHandler.setResourceBase(webappDirLocation);
        contextHandler.setInitParameter("env","LOCAL");
        contextHandler.setParentLoaderPriority(true);
        server.setHandler(contextHandler);

        server.start();

        return server;
    }

    public static void main(String[] args) throws Exception {
        Server server = runJettyServer(9111);
        server.join();
    }

}
