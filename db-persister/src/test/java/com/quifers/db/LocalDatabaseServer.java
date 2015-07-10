package com.quifers.db;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class LocalDatabaseServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDatabaseServer.class);
    private Server server;

    public void startServer(int port) throws SQLException {
        if (server == null) {
            server = Server.createTcpServer("-tcpPort", String.valueOf(port));
        }
        if (!server.isRunning(false)) {
            server.start();
            LOGGER.info(server.getStatus());
        } else {
            LOGGER.warn("Database server is already running!!");
        }
    }

    public void stopServer() {
        if (server != null) {
            server.stop();
        }
    }

}
