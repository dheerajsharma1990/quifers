package com.quifers.runners;

import com.quifers.db.SqlFilesExecutor;
import com.quifers.db.SqlScriptParser;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRunner.class);
    private static Server server;
    public static void runDatabaseServer(QuifersProperties quifersProperties) throws Exception {
        server = Server.createTcpServer("-tcpPort", "9092");
        LOGGER.info("Starting database sever...");
        server.start();
        Class.forName(quifersProperties.getDriverClass());
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        connection.prepareStatement("DROP ALL OBJECTS").execute();
        SqlFilesExecutor executor = new SqlFilesExecutor(connection, new SqlScriptParser());
        executor.execute("./src/main/resources/sql");
    }

    public static void stopDatabaseServer() {
        server.stop();
        server.shutdown();
    }

    public static void main(String[] args) throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
    }

}
