package com.quifers.db;

import com.quifers.Environment;
import com.quifers.properties.DBPersisterProperties;
import com.quifers.properties.DBPersisterPropertiesLoader;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;

public class LocalDatabaseRunner {

    private static Server server;

    public void runDatabaseServer(DBPersisterProperties dbPersisterProperties) throws Exception {
        server = Server.createTcpServer("-tcpPort", "9092");
        server.start();
        Class.forName(dbPersisterProperties.getDriverClass());
        Connection connection = DriverManager.getConnection(dbPersisterProperties.getUrl(),dbPersisterProperties.getUserName(),dbPersisterProperties.getPassword());
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
        DBPersisterProperties dbPersisterProperties = DBPersisterPropertiesLoader.loadDbPersisterProperties(Environment.LOCAL);
        new LocalDatabaseRunner().runDatabaseServer(dbPersisterProperties);
    }

}
