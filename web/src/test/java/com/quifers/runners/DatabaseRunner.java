package com.quifers.runners;

import com.quifers.db.SqlFilesExecutor;
import com.quifers.db.SqlScriptParser;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseRunner {

    public static Server runDatabaseServer() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        Server server = Server.createTcpServer("-tcpPort", "9123");
        server.start();
        Class.forName(quifersProperties.getDriverClass());
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        connection.prepareStatement("DROP ALL OBJECTS").execute();
        SqlFilesExecutor executor = new SqlFilesExecutor(connection, new SqlScriptParser());
        executor.execute("./src/main/resources/sql");
        return server;
    }

}
