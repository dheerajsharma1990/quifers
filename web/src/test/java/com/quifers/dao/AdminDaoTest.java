package com.quifers.dao;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
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
import static org.hamcrest.core.IsNull.notNullValue;

public class AdminDaoTest {

    private final AdminAccount adminAccount = new AdminAccount("adminUserName", "adminPassword");
    private final Admin admin = new Admin(adminAccount, "adminName", 9988776655l);

    private AdminDao dao;

    @Test
    public void shouldSaveAndGetAdmin() throws Exception {
        //when
        dao.saveAdmin(admin);
        Admin adminFromDb = dao.getAdmin(adminAccount.getUserId());

        //then
        assertThat(adminFromDb, notNullValue());
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
