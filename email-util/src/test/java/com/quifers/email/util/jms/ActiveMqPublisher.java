package com.quifers.email.util.jms;

import com.quifers.domain.Order;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


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



}
