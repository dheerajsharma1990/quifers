package com.quifers.db.impl;

import com.quifers.Environment;
import com.quifers.db.LocalDatabaseHelper;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import com.quifers.properties.DBPersisterProperties;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.quifers.db.LocalDatabaseServer.startServer;
import static com.quifers.db.LocalDatabaseServer.stopServer;

public class BaseDatabase {

    protected String envName = "local";
    protected Environment local = Environment.getEnvironment(envName);
    protected static LocalDatabaseHelper databaseHelper;
    protected static DaoFactory daoFactory;

    @BeforeSuite
    public void initialiseDBAndExecuteScripts() throws IOException, SQLException, ClassNotFoundException {
        local.loadLog4jProperties();
        DBPersisterProperties dbPersisterProperties = new DBPersisterProperties(local.loadProperties("db-persister.properties"));
        startServer(9092);
        Connection connection = connectToDb(dbPersisterProperties);
        databaseHelper = new LocalDatabaseHelper(connection);
        databaseHelper.executeSQLs();
        daoFactory = DaoFactoryBuilder.getDaoFactory(local);
    }

    private Connection connectToDb(DBPersisterProperties dbPersisterProperties) throws ClassNotFoundException, SQLException {
        Class.forName(dbPersisterProperties.getDriverClass());
        return DriverManager.getConnection(dbPersisterProperties.getUrl(), dbPersisterProperties.getUserName(), dbPersisterProperties.getPassword());
    }

    @AfterSuite
    public void shutdownDB() {
        stopServer();
    }

}
