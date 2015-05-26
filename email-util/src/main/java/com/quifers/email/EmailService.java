package com.quifers.email;

import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.jms.OrderReceiver;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import com.quifers.email.util.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.io.FileUtils;

import javax.jms.*;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
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

        initCredentialService(new JsonParser());

        EmailCreator emailCreator = new EmailCreator();
        EmailSender emailSender = new EmailSender(emailCreator);

        OrderReceiver orderReceiver = new OrderReceiver(messageConsumer, emailSender, CredentialsService.SERVICE);
        orderReceiver.listenForOrders();
    }

    public static void initCredentialService(JsonParser jsonParser) throws IOException {
        File file = new File("./target/credentials.json");
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
        Credentials credentials = jsonParser.parse(FileUtils.readFileToString(file));
        CredentialsService credentialsService = CredentialsService.SERVICE;
        credentialsService.setCredentials(credentials);
    }
}
