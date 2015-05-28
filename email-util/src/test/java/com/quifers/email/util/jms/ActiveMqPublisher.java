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
        Order order = new Order(10232l, "Dheeraj", 9988776655l, "mist_rock@rediffmail.com", "237, Phase III",
                "456, Phase IV", null, Arrays.asList(new OrderWorkflow(10232l, OrderState.BOOKED, new Date())));
        new ActiveMqPublisher().publishOrder(order);
    }

}
