package com.quifers.db;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class LocalDatabaseRunner {

    private static Server server;

    public void runDatabaseServer() throws Exception {
        server = Server.createTcpServer("-tcpPort", "9092");
        server.start();
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/~/quifersdb;DB_CLOSE_ON_EXIT=FALSE", "", "");
        connection.prepareStatement("DROP ALL OBJECTS").execute();
        SqlFilesSorter sqlFilesSorter = new SqlFilesSorter();
        SqlFilesExecutor executor = new SqlFilesExecutor(connection, sqlFilesSorter, new SqlScriptParser());
        executor.execute("../db-persister/src/main/resources/sql");
    }

    public void stopDatabaseServer() {
        server.stop();
        server.shutdown();
    }

    public static void main(String[] args) throws Exception {
        new LocalDatabaseRunner().runDatabaseServer();
    }

}
