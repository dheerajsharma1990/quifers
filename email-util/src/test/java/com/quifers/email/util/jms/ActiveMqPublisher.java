package com.quifers.email.util.jms;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;
import java.util.Date;


public class ActiveMqPublisher {

    private Session session;
    private MessageProducer producer;
    private Destination queue;

    public ActiveMqPublisher() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue("QUIFERS.EMAIL.QUEUE");
        producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
    }

    public void publishOrder(Order order) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(order);
        producer.send(objectMessage);
    }


    public static void main(String[] args) throws JMSException {
        long orderId = 100l;
        Order order = new Order(orderId, "name", 9988776655l, "email", "vehicle", "fromAddressHouseNumber",
                "fromAddressSociety", "fromAddressArea", "fromAddressCity", "toAddressHouseNumber", "toAddressSociety", "toAddressArea",
                "toAddressCity", 1, "estimate", "distance", 1, false, 2, true, null, Arrays.asList(new OrderWorkflow(orderId, OrderState.BOOKED, new Date())));
        new ActiveMqPublisher().publishOrder(order);
    }

}
