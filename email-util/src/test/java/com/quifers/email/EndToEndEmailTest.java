package com.quifers.email;

import com.quifers.domain.*;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.BillDetailsEmailCreator;
import com.quifers.email.helpers.EmailHttpRequestSender;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.helpers.NewOrderEmailCreator;
import com.quifers.email.util.CredentialsService;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Test
public class EndToEndEmailTest {

    private CredentialsService credentialsService;

    /**
     * Kindly generate credentials from CredentialsGenerator.java before running this test..
     */
    @Test
    public void shouldSendEmailsSuccessfully() throws Exception {
        //given
        EmailSender emailSender = new EmailSender(new EmailHttpRequestSender(new HttpRequestSender()), new EmailRequestBuilder());
        Order order = getOrder();

        //when
        emailSender.sendEmail(credentialsService.getCredentials(), new NewOrderEmailCreator("quifersdev@gmail.com"), order);
        emailSender.sendEmail(credentialsService.getCredentials(), new BillDetailsEmailCreator("quifersdev@gmail.com"), order);

        //then
    }


    @BeforeClass
    public void initialiseEmailService() throws Exception {
        System.setProperty("env", "local");
        credentialsService = new CredentialsService(CredentialsService.DEFAULT_DIR, new JsonParser());
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
        Order order =  new Order(orderId, client, "vehicle", addresses, 1, "estimate", 1, false, 2, true, null, workflowSet);
        order.setDistance(new Distance(orderId,2));
        return order;
    }

}
