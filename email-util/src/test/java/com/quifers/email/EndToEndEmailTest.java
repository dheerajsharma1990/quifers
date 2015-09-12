package com.quifers.email;

import com.quifers.Environment;
import com.quifers.domain.Address;
import com.quifers.domain.Client;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.*;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Test(enabled = false)
public class EndToEndEmailTest {

    @Test(enabled = false)
    public void shouldSendEmailsSuccessfully() throws Exception {
        //given
        System.setProperty("env", "local");
        EmailService.main(null);

        //when
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("EMAIL.ACTIVEMQ.SEND.CLIENT");
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination queue = session.createQueue("QUIFERS.EMAIL.QUEUE");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(getOrder());
        objectMessage.setStringProperty("EMAIL_TYPE", EmailType.NEW_ORDER.name());
        producer.send(objectMessage);

        //then
        Thread.sleep(10 * 1000);
    }


    @BeforeClass
    public void initialiseEmailService() throws Exception {
        loadLog4jProperties(Environment.LOCAL);
        startActiveMq();
    }

    private void startActiveMq() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("WebActiveMqBroker");
        broker.setDedicatedTaskRunner(false);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.addConnector("tcp://localhost:61616");
        broker.setUseShutdownHook(false);
        broker.start();
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
