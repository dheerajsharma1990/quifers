package com.quifers.email;

import com.quifers.email.jms.OrderListener;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailSender;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.mail.MessagingException;
import java.io.IOException;

public class EmailService {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String EMAIL_QUEUE = "QUIFERS.EMAIL.QUEUE";

    public static void main(String[] args) throws JMSException, IOException, MessagingException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(EMAIL_QUEUE);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        connection.start();

        EmailCreator emailCreator = new EmailCreator();
        EmailSender emailSender = new EmailSender(emailCreator);

        OrderListener orderListener = new OrderListener(emailSender, messageConsumer);
        orderListener.listenForOrders();
    }
}
