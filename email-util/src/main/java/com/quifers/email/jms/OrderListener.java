package com.quifers.email.jms;

import com.quifers.domain.Order;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.EmailCreator;
import com.quifers.email.util.EmailSender;
import com.quifers.email.util.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

public class OrderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);
    private final EmailSender emailSender;
    private final EmailCreator emailCreator;
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String EMAIL_QUEUE = "QUIFERS.EMAIL.QUEUE";

    public OrderListener(EmailSender emailSender, EmailCreator emailCreator) {
        this.emailSender = emailSender;
        this.emailCreator = emailCreator;
    }

    public void listenForOrders() throws JMSException, IOException, MessagingException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(EMAIL_QUEUE);
        MessageConsumer consumer = session.createConsumer(queue);
        connection.start();
        ActiveMQObjectMessage message = (ActiveMQObjectMessage) consumer.receive();
        Order order = (Order) message.getObject();
        LOGGER.info("Order Received: {}", order);
        JsonParser parser = new JsonParser();
        Credentials credentials = parser.parse(FileUtils.readFileToString(new File("./target/credentials.json")));
        emailOrderDetails(credentials, order);
        message.acknowledge();
    }

    private void emailOrderDetails(Credentials credentials, Order order) throws IOException, MessagingException {
        MimeMessage mimeMessage = emailCreator.createEmail(order, "dheeraj.sharma.aws@gmail.com");
        emailSender.sendEmail(credentials, mimeMessage);
    }

}
