package com.quifers.dao;

import com.quifers.domain.Admin;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AdminDaoTest {

    private final Admin admin = new Admin("adminUserName", "adminName", 9988776655l);
    private AdminDao dao;

    @Test
    public void shouldSaveAdmin() throws Exception {
        //when
        int rowsUpdated = dao.saveAdmin(admin);

        //then
        assertThat(rowsUpdated, is(1));
    }

    @Test(dependsOnMethods = "shouldSaveAdmin")
    public void shouldGetAdmin() throws Exception {
        //when
        Admin adminFromDb = dao.getAdmin(admin.getUserId());

        //then
        assertThat(adminFromDb, is(admin));
    }

    @BeforeClass
    public void initialiseDao() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new AdminDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }
}
