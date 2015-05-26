package com.quifers.email.util.jms;

import com.quifers.domain.Order;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.quifers.email.jms.OrderListener.ACTIVEMQ_URL;

public class ActiveMqPublisher {

    public void publishOrderOnQueue(String queueName, Order order) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(order);
        producer.send(objectMessage);
    }

}
