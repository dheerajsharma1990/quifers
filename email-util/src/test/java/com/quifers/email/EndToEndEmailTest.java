package com.quifers.email;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.db.LocalDatabaseRunner;
import com.quifers.domain.Address;
import com.quifers.domain.Client;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.EmailHttpRequestSender;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.helpers.OrderEmailCreator;
import com.quifers.email.util.CredentialsService;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Test(enabled = false)
public class EndToEndEmailTest {

    private OrderDao orderDao;

    /**
     *
     * Kindly generate credentials from CredentialsGenerator.java before running this test..
     */
    @Test(enabled = false)
    public void shouldSendEmailSuccessfully() throws Exception {
        //given
        EmailSender emailSender = new EmailSender(new EmailHttpRequestSender(new HttpRequestSender()), new EmailRequestBuilder());
        Order order = getOrder();
        orderDao.saveOrder(order);

        //when
        emailSender.sendEmail(CredentialsService.getCredentials(), new OrderEmailCreator(orderDao),order.getOrderId(),"quifersdev@gmail.com");

        //then
    }

    @BeforeClass
    public void initialiseEmailService() throws Exception {
        System.setProperty("env", "local");
        new LocalDatabaseRunner().runDatabaseServer();
        orderDao = DaoFactoryBuilder.getDaoFactory(Environment.LOCAL).getOrderDao();
        EmailService.initialiseCredentialsService(new JsonParser());
    }

    private Order getOrder() {
        String orderId = "100";
        Set<OrderWorkflow> workflowSet = new HashSet<>();
        workflowSet.add(new OrderWorkflow(orderId, OrderState.BOOKED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRIP_STARTED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRANSIT_STARTED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRANSIT_ENDED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRIP_ENDED, new Date()));

        Client client = new Client(orderId, "name", 9988776655l, "dheerajsharma1990@gmail.com");
        Address pickUpAddress = new Address(orderId, AddressType.PICKUP, "fromAddressHouseNumber", "fromAddressSociety", "fromAddressArea", "fromAddressCity");
        Address dropOffAddress = new Address(orderId, AddressType.DROP, "toAddressHouseNumber", "toAddressSociety", "toAddressArea", "toAddressCity");
        Set<Address> addresses = new HashSet<>();
        addresses.add(pickUpAddress);
        addresses.add(dropOffAddress);
        return new Order(orderId, client, "vehicle", addresses, 1, "estimate", 2, 1, false, 2, true, null, workflowSet);
    }

}
