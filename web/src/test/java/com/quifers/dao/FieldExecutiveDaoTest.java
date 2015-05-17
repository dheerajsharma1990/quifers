package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class FieldExecutiveDaoTest {

    private final String USER_NAME = "username";
    private final FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(USER_NAME, "password");
    private final FieldExecutive fieldExecutive = new FieldExecutive(USER_NAME, "name", 9999778899l);

    private FieldExecutiveDao dao;

    @Test
    public void shouldSaveFieldExecutive() throws Exception {
        //when
        int rowsUpdated = dao.saveFieldExecutive(fieldExecutive);

        //then
        assertThat(rowsUpdated, is(1));
    }

    @Test(dependsOnMethods = "shouldSaveFieldExecutive")
    public void shouldGetFieldExecutive() throws Exception {
        //when
        FieldExecutive fieldExecutiveFromDb = dao.getFieldExecutive(USER_NAME);

        //then
        assertThat(fieldExecutiveFromDb, is(fieldExecutive));
    }

    @BeforeClass
    public void initialiseDaoAndCreateAccount() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        createFieldExecutiveAccount(connection);
        dao = new FieldExecutiveDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }

    private void createFieldExecutiveAccount(Connection connection) throws SQLException {
        FieldExecutiveAccountDao dao = new FieldExecutiveAccountDao(connection);
        dao.saveAccount(fieldExecutiveAccount);
        FieldExecutiveAccount account = dao.getAccount(fieldExecutiveAccount.getUserId());
        assertThat(account, notNullValue());
    }
}
