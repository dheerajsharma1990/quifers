package com.quifers.db.impl;

import com.quifers.Environment;
import com.quifers.db.LocalDatabaseHelper;
import com.quifers.db.LocalDatabaseServer;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import com.quifers.properties.DBPersisterProperties;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.quifers.properties.DBPersisterPropertiesLoader.loadDbPersisterProperties;

public class BaseDatabase {

    private final LocalDatabaseServer localDatabaseServer = new LocalDatabaseServer();
    protected LocalDatabaseHelper databaseHelper;
    protected DaoFactory daoFactory;

    @BeforeSuite
    public void initialiseDBAndExecuteScripts() throws IOException, SQLException, ClassNotFoundException {
        Environment environment = getEnvironment();
        DBPersisterProperties dbPersisterProperties = loadDbPersisterProperties(environment);
        localDatabaseServer.startServer(9092);
        Connection connection = connectToDb(dbPersisterProperties);
        databaseHelper = new LocalDatabaseHelper(connection);
        databaseHelper.executeSQLs();
        daoFactory = DaoFactoryBuilder.getDaoFactory(environment);
    }

    private Environment getEnvironment() {
        String env = System.getProperty("env");
        return env != null ? Environment.valueOf(env.toUpperCase()) : Environment.LOCAL;
    }

    private Connection connectToDb(DBPersisterProperties dbPersisterProperties) throws ClassNotFoundException, SQLException {
        Class.forName(dbPersisterProperties.getDriverClass());
        return DriverManager.getConnection(dbPersisterProperties.getUrl(), dbPersisterProperties.getUserName(), dbPersisterProperties.getPassword());
    }

    @AfterSuite
    public void shutdownDB() {
        localDatabaseServer.stopServer();
    }

}
