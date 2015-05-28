package com.quifers.dao;

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

public class AdminAccountDaoTest {

    private final AdminAccount account = new AdminAccount("adminUserName", "adminPassword");
    private AdminAccountDao dao;

    @Test
    public void shouldSaveAdminAccount() throws Exception {
        //when
        int rowsUpdated = dao.saveAccount(account);

        //then
        assertThat(rowsUpdated, is(1));
    }

    @Test(dependsOnMethods = "shouldSaveAdminAccount")
    public void shouldGetAdminAccount() throws Exception {
        //when
        AdminAccount accountFromDb = dao.getAccount(account.getUserId());

        //then
        assertThat(accountFromDb, is(account));
    }

    @BeforeClass
    public void initialiseDao() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new AdminAccountDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }
}
