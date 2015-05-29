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
import java.util.List;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FieldExecutiveDaoTest {

    private final String USER_NAME = "username";
    private final FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(USER_NAME, "password");
    private final FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveAccount, "name", 9999778899l);

    private FieldExecutiveDao dao;

    @Test
    public void shouldSaveAndGetFieldExecutive() throws Exception {
        //when
        dao.saveFieldExecutive(fieldExecutive);
        FieldExecutive fieldExecutiveFromDb = dao.getFieldExecutive(fieldExecutive.getAccount().getUserId());
        //then
        assertThat(fieldExecutiveFromDb, is(fieldExecutive));
    }


    @Test(dependsOnMethods = "shouldSaveAndGetFieldExecutive")
    public void shouldGetAllFieldExecutives() throws Exception {
        //when
        List<FieldExecutive> fieldExecutivesFromDb = dao.getAllFieldExecutives();

        //then
        assertThat(fieldExecutivesFromDb.size(), is(1));
    }

    @BeforeClass
    public void initialiseDaoAndCreateAccount() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new FieldExecutiveDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }

}
