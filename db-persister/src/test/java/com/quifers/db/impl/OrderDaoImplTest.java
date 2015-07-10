package com.quifers.db.impl;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.*;
import com.quifers.domain.builders.OrderBuilder;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class OrderDaoImplTest extends BaseDatabase {

    private FieldExecutiveId fieldExecutiveId = new FieldExecutiveId("fe");
    private FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(fieldExecutiveId, "pass");
    private FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveId, "name", 99l);
    private OrderId orderId = new OrderId("QUIFID1");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private OrderDao orderDao;
    private FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private FieldExecutiveDao fieldExecutiveDao;

    @Test
    public void shouldGetOrdersIfBookingDateIsWithinRange() throws Exception {
        //given
        orderDao = daoFactory.getOrderDao();
        orderDao.saveOrder(buildOrder(orderId, OrderState.BOOKED, "28/06/2015 15:15", true));
        orderDao.saveOrder(buildOrder(new OrderId("QUIFID11"), OrderState.BOOKED, "29/06/2015 00:00", true));
        orderDao.saveOrder(new OrderBuilder("QUIFID90").addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("28/06/2015 10:10"), false)
                .addOrderWorkflow(OrderState.COMPLETED, dateFormat.parse("28/06/2015 15:15"), true)
                .addFieldExecutive(fieldExecutive.getFieldExecutiveId().getUserId(), fieldExecutive.getName(), fieldExecutive.getMobileNumber())
                .buildOrder());


        //when
        Collection<Order> ordersFromDb = orderDao.getBookedOrders(fieldExecutive, new Day("28/06/2015"));

        //then
        assertThat(ordersFromDb.size(), is(1));
    }

    @Test
    public void shouldGetUnassignedOrders() throws Exception {
        //given
        orderDao = daoFactory.getOrderDao();
        orderDao.saveOrder(new Order(new OrderId("QUIFID10")));
        orderDao.saveOrder(new Order(new OrderId("QUIFID11")));
        Order order = new Order(new OrderId("QUIFID12"));
        order.setFieldExecutive(fieldExecutive);
        orderDao.saveOrder(order);

        //when
        Collection<Order> unassignedOrders = orderDao.getUnassignedOrders();

        //then
        assertThat(unassignedOrders.size(), is(2));
    }

    @Test
    public void shouldGetAssignedOrders() throws Exception {
        //given
        orderDao = daoFactory.getOrderDao();
        orderDao.saveOrder(new OrderBuilder("QUIFID20")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("25/06/2015 15:15"), false)
                .addOrderWorkflow(OrderState.COMPLETED, dateFormat.parse("27/06/2015 15:15"), true).buildOrder());
        orderDao.saveOrder(new OrderBuilder("QUIFID21")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("26/06/2015 15:15"), true).buildOrder());
        orderDao.saveOrder(new OrderBuilder("QUIFID22")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("28/06/2015 15:15"), true).buildOrder());

        //when
        Collection<Order> assignedOrders = orderDao.getOrders(OrderState.BOOKED, new Day("26/06/2015"), new Day("28/06/2015"));

        //then
        assertThat(assignedOrders.size(), is(1));
    }

    @Test
    public void shouldGetAllCompletedOrders() throws Exception {
        //given
        orderDao = daoFactory.getOrderDao();
        orderDao.saveOrder(buildOrder(new OrderId("QUIF1"), OrderState.BOOKED, "20/06/2015 15:15", true));
        orderDao.saveOrder(buildOrder(new OrderId("QUIF2"), OrderState.COMPLETED, "19/06/2015 15:15", true));
        orderDao.saveOrder(buildOrder(new OrderId("QUIF3"), OrderState.COMPLETED, "18/06/2015 15:15", true));

        //when
        Collection<Order> completedOrders = orderDao.getOrders(OrderState.COMPLETED, new Day("18/06/2015"),
                new Day("29/06/2015"));

        //then
        assertThat(completedOrders.size(), is(2));
    }


    private Order buildOrder(OrderId orderId, OrderState orderState, String dateString, boolean currentState) throws ParseException {
        Order order = new Order(orderId);
        order.setFieldExecutive(fieldExecutive);
        order.addOrderWorkflow(new OrderWorkflow(orderId, orderState, dateFormat.parse(dateString), currentState));
        return order;
    }

    @BeforeClass
    public void saveFieldExecutive() throws Exception {
        orderDao = daoFactory.getOrderDao();
        fieldExecutiveAccountDao = daoFactory.getFieldExecutiveAccountDao();
        fieldExecutiveDao = daoFactory.getFieldExecutiveDao();
    }

    @BeforeMethod
    public void addFieldExecutive() throws Exception {
        fieldExecutiveAccountDao.saveFieldExecutiveAccount(fieldExecutiveAccount);
        fieldExecutiveDao.saveFieldExecutive(fieldExecutive);
    }

    @AfterMethod
    public void clearTables() throws Exception {
        databaseHelper.cleanAllTables();
    }
}
