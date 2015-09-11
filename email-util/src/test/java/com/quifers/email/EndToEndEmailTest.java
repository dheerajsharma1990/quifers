package com.quifers.email;

import com.quifers.Environment;
import com.quifers.domain.Address;
import com.quifers.domain.Client;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.*;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.properties.PropertiesLoader;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Test
public class EndToEndEmailTest {

    private Credentials credentials;

    @Test
    public void shouldSendEmailsSuccessfully() throws Exception {
        //given
        EmailSender emailSender = new EmailSender(new EmailHttpRequestSender(new HttpRequestSender()), new EmailRequestBuilder());
        Order order = getOrder();

        //when
        emailSender.sendEmail(credentials, new NewOrderEmailCreator("quifersdev@gmail.com"), order);
        emailSender.sendEmail(credentials, new BillDetailsEmailCreator("quifersdev@gmail.com"), order);
    }


    @BeforeClass
    public void initialiseEmailService() throws Exception {
        Environment local = Environment.LOCAL;
        loadLog4jProperties(local);
        EmailUtilProperties emailUtilProperties = PropertiesLoader.loadEmailUtilProperties(local);
        credentials = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(emailUtilProperties), new JsonParser(), emailUtilProperties.getRefreshToken()).getRefreshedCredentials();
    }

    private void loadLog4jProperties(Environment environment) {
        InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream("properties/" + environment.name().toLowerCase() + "/log4j.properties");
        PropertyConfigurator.configure(inputStream);
    }

    private Order getOrder() {
        OrderId orderId = new OrderId("100");
        Set<OrderWorkflow> workflowSet = new HashSet<>();
        workflowSet.add(new OrderWorkflow(orderId, OrderState.BOOKED, new Date(), true));

        Client client = new Client(orderId, "name", 9988776655l, "dheerajsharma1990@gmail.com");
        Address pickUpAddress = new Address(orderId, AddressType.PICKUP, "fromAddressHouseNumber", "fromAddressSociety", "fromAddressArea", "fromAddressCity");
        Address dropOffAddress = new Address(orderId, AddressType.DROP, "toAddressHouseNumber", "toAddressSociety", "toAddressArea", "toAddressCity");
        Set<Address> addresses = new HashSet<>();
        addresses.add(pickUpAddress);
        addresses.add(dropOffAddress);
        return new Order(orderId, client, "vehicle", addresses, 1, "estimate", 10, 1, false, 2, true, null, workflowSet, 0, 0);
    }

}
