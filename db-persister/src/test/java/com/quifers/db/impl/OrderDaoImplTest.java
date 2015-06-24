package com.quifers.db.impl;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.db.LocalDatabaseRunner;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.builders.OrderBuilder;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrderDaoImplTest {

    private FieldExecutiveId fieldExecutiveId = new FieldExecutiveId("fe");
    private FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(fieldExecutiveId, "pass");
    private FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveId, "name", 99l);
    private OrderId orderId = new OrderId("QUIFID1");
    private DaoFactory daoFactory;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
    private OrderDao orderDao;

    @Test
    public void shouldGetOrdersIfBookingDateIsWithinRange() throws Exception {
        //given
        orderDao.saveOrder(buildOrder(orderId, OrderState.BOOKED, "28/06/2015 15:15", true));

        //when
        Date bookingDateTime = dateFormat.parse("28/06/2015 00:00");
        Collection<Order> ordersFromDb = orderDao.getBookedOrders(fieldExecutive, bookingDateTime);

        //then
        assertThat(ordersFromDb.size(), is(1));
    }

    @Test
    public void shouldGetUnassignedOrders() throws Exception {
        //given
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
        orderDao.saveOrder(new OrderBuilder("QUIFID20")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("25/06/2015 15:15"), false)
                .addOrderWorkflow(OrderState.COMPLETED, dateFormat.parse("27/06/2015 15:15"), true).buildOrder());
        orderDao.saveOrder(new OrderBuilder("QUIFID21")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("26/06/2015 15:15"), true).buildOrder());
        orderDao.saveOrder(new OrderBuilder("QUIFID22")
                .addOrderWorkflow(OrderState.BOOKED, dateFormat.parse("28/06/2015 15:15"), true).buildOrder());

        //when
        Collection<Order> assignedOrders = orderDao.getAssignedOrders(dayFormat.parse("24/06/2015"), dayFormat.parse("28/06/2015"));

        //then
        assertThat(assignedOrders.size(), is(1));
    }
    @Test
    public void shouldGetAllCompletedOrders() throws Exception {
        //given
        orderDao.saveOrder(buildOrder(new OrderId("QUIF1"), OrderState.BOOKED, "20/06/2015 15:15", true));
        orderDao.saveOrder(buildOrder(new OrderId("QUIF2"), OrderState.COMPLETED, "19/06/2015 15:15", true));
        orderDao.saveOrder(buildOrder(new OrderId("QUIF3"), OrderState.COMPLETED, "18/06/2015 15:15", true));

        //when
        Collection<Order> completedOrders = orderDao.getCompletedOrders(dateFormat.parse("18/06/2015 00:00"),
                dateFormat.parse("20/06/2015 00:00"));

        //then
        assertThat(completedOrders.size(), is(2));
    }


    private Order buildOrder(OrderId orderId, OrderState orderState, String dateString, boolean currentState) throws ParseException {
        Order order = new Order(orderId);
        order.setFieldExecutive(fieldExecutive);
        order.addOrderWorkflow(new OrderWorkflow(orderId, orderState, dateFormat.parse(dateString), currentState));
        return order;
    }


    @BeforeMethod
    public void startDatabaseAndAddFieldExecutives() throws Exception {
        new LocalDatabaseRunner().runDatabaseServer();
        daoFactory = DaoFactoryBuilder.getDaoFactory(Environment.LOCAL);
        saveFieldExecutive();
        orderDao = daoFactory.getOrderDao();
    }

    private void saveFieldExecutive() throws Exception {
        daoFactory.getFieldExecutiveAccountDao().saveFieldExecutiveAccount(fieldExecutiveAccount);
        daoFactory.getFieldExecutiveDao().saveFieldExecutive(fieldExecutive);
    }

    @AfterMethod
    public void stopDatabase() throws Exception {
        new LocalDatabaseRunner().stopDatabaseServer();
    }

}
