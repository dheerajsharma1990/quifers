package com.quifers.email.util.jms;

import com.quifers.domain.Order;
import com.quifers.email.EmailService;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class ActiveMqPublisher {

    private Session session;
    private MessageProducer producer;
    private Destination queue;

    public ActiveMqPublisher() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(EmailService.ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(EmailService.EMAIL_QUEUE);
        producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
    }

    public void publishOrder(Order order) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(order);
        producer.send(objectMessage);
    }

    public MessageConsumer getMessageConsumer() throws JMSException {
        return session.createConsumer(queue);
    }

}
