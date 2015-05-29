package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.properties.Environment;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.QuifersProperties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class OrderDaoTest {

    private final String USER_NAME = "userName";
    private final Date bookedDate = new Date();
    private final Date tripStartDate = new Date();
    private final long ORDER_ID = 10l;
    private final OrderWorkflow bookedWorkflow = new OrderWorkflow(ORDER_ID, OrderState.BOOKED, bookedDate);
    private final OrderWorkflow tripStartWorkflow = new OrderWorkflow(ORDER_ID, OrderState.TRIP_STARTED, tripStartDate);
    private final Collection<OrderWorkflow> orderWorkflows = new HashSet<>(Arrays.asList(bookedWorkflow, tripStartWorkflow));
    private final Order order = new Order(ORDER_ID, "name", 9988776655l, "email", "vehicle", "fromAddressHouseNumber",
            "fromAddressSociety", "fromAddressArea", "fromAddressCity", "toAddressHouseNumber", "toAddressSociety", "toAddressArea",
            "toAddressCity", 1, "estimate", "distance", 1, false, 2, true, null, orderWorkflows);
    private final FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(USER_NAME, "password");
    private final FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveAccount, "name", 9988776655l);

    private OrderDao dao;
    private FieldExecutiveDao fieldExecutiveDao;

    @Test
    public void shouldSaveAndGetOrder() throws Exception {
        //when
        dao.saveOrder(order);
        Order orderFromDb = dao.getOrder(order.getOrderId());

        //then
        assertThat(orderFromDb, is(order));
    }


    @Test(dependsOnMethods = "shouldSaveAndGetOrder")
    public void shouldAssignFieldExecutiveToOrder() throws Exception {
        //given
        fieldExecutiveExist();

        //when
        int rowsUpdate = dao.assignFieldExecutiveToOrder(order.getOrderId(), fieldExecutive.getAccount().getUserId());

        //then
        assertThat(rowsUpdate, is(1));
    }

    @Test(dependsOnMethods = "shouldAssignFieldExecutiveToOrder")
    public void shouldGetOrderByFieldExecutiveId() throws Exception {
        List<Order> ordersFromDb = dao.getOrderByFieldExecutiveId(fieldExecutive.getAccount().getUserId());

        assertThat(ordersFromDb.size(), is(1));
    }

    private void fieldExecutiveExist() throws SQLException {
        fieldExecutiveDao.saveFieldExecutive(fieldExecutive);
        FieldExecutive executive = fieldExecutiveDao.getFieldExecutive(fieldExecutive.getAccount().getUserId());
        assertThat(executive, notNullValue());
    }

    @BeforeClass
    public void initialiseDao() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new OrderDao(connection);
        fieldExecutiveDao = new FieldExecutiveDao(connection);
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }
}
