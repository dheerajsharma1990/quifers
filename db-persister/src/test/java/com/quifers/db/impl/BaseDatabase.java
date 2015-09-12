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
import static com.quifers.properties.DBPersisterPropertiesLoader.loadDbPersisterProperties;

public class BaseDatabase {

    protected Environment local = Environment.LOCAL;
    protected static LocalDatabaseHelper databaseHelper;
    protected static DaoFactory daoFactory;

    @BeforeSuite
    public void initialiseDBAndExecuteScripts() throws IOException, SQLException, ClassNotFoundException {
        DBPersisterProperties dbPersisterProperties = loadDbPersisterProperties(local);
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

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        new BaseDatabase().initialiseDBAndExecuteScripts();
    }

}
