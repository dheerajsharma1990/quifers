package com.quifers.dao;

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
import java.util.Collection;
import java.util.Date;

import static com.quifers.runners.DatabaseRunner.runDatabaseServer;
import static com.quifers.runners.DatabaseRunner.stopDatabaseServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class OrderWorkflowDaoTest {

    private final Date bookedDate = new Date();
    private final Date tripStartDate = new Date();
    private final long ORDER_ID = 10l;
    private final OrderWorkflow bookedWorkflow = new OrderWorkflow(ORDER_ID, OrderState.BOOKED, bookedDate);
    private final OrderWorkflow tripStartWorkflow = new OrderWorkflow(ORDER_ID, OrderState.TRIP_STARTED, tripStartDate);
    private OrderWorkflowDao dao;
    private OrderDao orderDao;

    @Test
    public void shouldSaveOrderWorkflows() throws Exception {
        //given
        orderExists();

        //when
        int rowUpdate1 = dao.saveOrderWorkflow(bookedWorkflow);
        int rowUpdate2 = dao.saveOrderWorkflow(tripStartWorkflow);

        //then
        assertThat(rowUpdate1, is(1));
        assertThat(rowUpdate2, is(1));
    }

    @Test(dependsOnMethods = "shouldSaveOrderWorkflows")
    public void shouldGetOrderWorkflows() throws Exception {
        //when

        Collection<OrderWorkflow> orderWorkflows = dao.getOrderWorkflows(ORDER_ID);

        //then
        assertThat(orderWorkflows.size(), is(2));
        assertThat("Booked workflow not present.", orderWorkflows, hasItem(bookedWorkflow));
        assertThat("Trip Started workflow not present.", orderWorkflows, hasItem(tripStartWorkflow));
    }

    @BeforeClass
    public void initialiseDao() throws Exception {
        QuifersProperties quifersProperties = PropertiesLoader.loadProperties(Environment.LOCAL);
        runDatabaseServer(quifersProperties);
        Connection connection = DriverManager.getConnection(quifersProperties.getDbUrl());
        dao = new OrderWorkflowDao(connection);
        orderDao = new OrderDao(connection);
    }

    private void orderExists() throws SQLException {
        Order order = new Order(ORDER_ID, "name", 9988776655l, "email", "fromAddress", "toAddress", null);
        orderDao.saveOrder(order);
        Order orderFromDb = orderDao.getOrder(ORDER_ID);
        assertThat(orderFromDb, notNullValue());
    }

    @AfterClass
    public void shutDownDatabaseServer() {
        stopDatabaseServer();
    }
}
