package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.Order;
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

public class OrderDaoTest {

    private final String USER_NAME = "userName";
    private final Order order = new Order(10l, "name", 9988776655l, "email", "fromAddress", "toAddress", null);
    private final FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(USER_NAME, "password");
    private final FieldExecutive fieldExecutive = new FieldExecutive(USER_NAME, "name", 9988776655l);

    private OrderDao dao;
    private FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private FieldExecutiveDao fieldExecutiveDao;

    @Test
    public void shouldSaveOrder() throws Exception {
        //when
        int rowsUpdated = dao.saveOrder(order);

        //then
        assertThat(rowsUpdated, is(1));
    }

    @Test(dependsOnMethods = "shouldSaveOrder")
    public void shouldGetOrder() throws Exception {
        //when
        Order orderFromDb = dao.getOrder(order.getOrderId());

        //then
        assertThat(orderFromDb, is(order));
    }

    @Test(dependsOnMethods = "shouldGetOrder")
    public void shouldAssignFieldExecutiveToOrder() throws Exception {
        //given
        fieldExecutiveExist();

        //when
        int rowsUpdate = dao.assignFieldExecutiveToOrder(order.getOrderId(), fieldExecutive.getUserId());

        //then
        assertThat(rowsUpdate, is(1));
    }

    private void fieldExecutiveExist() throws SQLException {
        fieldExecutiveAccountDao.saveAccount(fieldExecutiveAccount);
        FieldExecutiveAccount account = fieldExecutiveAccountDao.getAccount(fieldExecutiveAccount.getUserId());
        assertThat(account, notNullValue());
        fieldExecutiveDao.saveFieldExecutive(fieldExecutive);
        FieldExecutive executive = fieldExecutiveDao.getFieldExecutive(fieldExecutive.getUserId());
        assertThat(fieldExecutive, notNullValue());
    }

    @BeforeClass
    public void initialiseDao() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new OrderDao(connection);
        fieldExecutiveAccountDao = new FieldExecutiveAccountDao(connection);
        fieldExecutiveDao = new FieldExecutiveDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }
}
