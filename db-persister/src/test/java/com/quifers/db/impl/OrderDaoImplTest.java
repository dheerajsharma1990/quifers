package com.quifers.db.impl;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.db.LocalDatabaseRunner;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

    @Test
    public void shouldGetOrdersIfBookingDateIsWithinRange() throws Exception {
        //given
        Order order = buildOrder(orderId, "28/06/2015 15:15");
        OrderDao orderDao = daoFactory.getOrderDao();
        orderDao.saveOrder(order);

        //when
        Date bookingDateTime = dateFormat.parse("28/06/2015 00:00");
        Collection<Order> ordersFromDb = orderDao.getBookedOrders(fieldExecutive, bookingDateTime);

        //then
        assertThat(ordersFromDb.size(), is(1));
    }


    private Order buildOrder(OrderId orderId, String dateString) throws ParseException {
        Order order = new Order(orderId);
        order.setFieldExecutive(fieldExecutive);
        order.addOrderWorkflow(new OrderWorkflow(orderId, OrderState.BOOKED, dateFormat.parse(dateString)));
        return order;
    }

    @BeforeClass
    public void startDatabaseAndAddFieldExecutives() throws Exception {
        new LocalDatabaseRunner().runDatabaseServer();
        daoFactory = DaoFactoryBuilder.getDaoFactory(Environment.LOCAL);
        saveFieldExecutive();
    }

    private void saveFieldExecutive() throws Exception {
        daoFactory.getFieldExecutiveAccountDao().saveFieldExecutiveAccount(fieldExecutiveAccount);
        daoFactory.getFieldExecutiveDao().saveFieldExecutive(fieldExecutive);
    }

    @AfterClass
    public void stopDatabase() throws Exception {
        new LocalDatabaseRunner().stopDatabaseServer();
    }

}
