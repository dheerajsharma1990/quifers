package com.quifers.db;

import com.quifers.Environment;
import com.quifers.properties.DBPersisterProperties;
import org.apache.log4j.PropertyConfigurator;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import static com.quifers.properties.DBPersisterPropertiesLoader.loadDbPersisterProperties;

public class LocalDatabaseRunner {

    private static Server server;

    public void runDatabaseServer(Environment environment) throws Exception {
        DBPersisterProperties dbPersisterProperties = loadProperties(environment);
        server = Server.createTcpServer("-tcpPort", "9092");
        server.start();
        Class.forName(dbPersisterProperties.getDriverClass());
        Connection connection = DriverManager.getConnection(dbPersisterProperties.getUrl(), dbPersisterProperties.getUserName(), dbPersisterProperties.getPassword());
        connection.prepareStatement("DROP ALL OBJECTS").execute();
        SqlFilesSorter sqlFilesSorter = new SqlFilesSorter();
        SqlFilesExecutor executor = new SqlFilesExecutor(connection, sqlFilesSorter, new SqlScriptParser());
        executor.execute("../db-persister/src/main/resources/sql");
    }

    private DBPersisterProperties loadProperties(Environment environment) throws IOException {
        loadLog4jProperties(environment);
        return loadDbPersisterProperties(environment);
    }

    public void stopDatabaseServer() {
        server.stop();
        server.shutdown();
    }

    public static void main(String[] args) throws Exception {
        new LocalDatabaseRunner().runDatabaseServer(Environment.LOCAL);
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = LocalDatabaseRunner.class.getClassLoader().getResourceAsStream("properties/" + environment.name().toLowerCase() + "/log4j.properties");
        PropertyConfigurator.configure(inputStream);
    }

}
