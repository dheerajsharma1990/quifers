package com.quifers.email;

import com.quifers.email.helpers.CredentialsRefresher;
import com.quifers.email.helpers.CredentialsRefresherTask;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.jms.OrderReceiver;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.properties.Environment;
import com.quifers.email.properties.PropertiesLoader;
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
import java.util.Timer;

public class EmailService {

    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String EMAIL_QUEUE = "QUIFERS.EMAIL.QUEUE";
    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) throws JMSException, IOException, MessagingException {
        EmailUtilProperties properties = getEmailUtilProperties();
        MessageConsumer messageConsumer = startActiveMq();

        initCredentialsService(jsonParser);
        startCredentialsRefreshingTask(new CredentialsRefresher(properties, jsonParser));

        receiveOrders(messageConsumer);
    }

    private static EmailUtilProperties getEmailUtilProperties() throws IOException {
        Environment environment = Environment.valueOf(System.getProperty("env"));
        return new PropertiesLoader().getEmailUtilProperties(environment);
    }

    private static void receiveOrders(MessageConsumer messageConsumer) throws JMSException, IOException, MessagingException {
        EmailSender emailSender = new EmailSender(new EmailCreator());
        OrderReceiver orderReceiver = new OrderReceiver(messageConsumer, emailSender, CredentialsService.SERVICE);
        orderReceiver.receiveOrders();
    }

    private static void startCredentialsRefreshingTask(CredentialsRefresher credentialsRefresher) {
        CredentialsRefresherTask refresherTask = new CredentialsRefresherTask(credentialsRefresher);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(refresherTask, 0, 30 * 60 * 1 * 1000);
    }

    private static MessageConsumer startActiveMq() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(EMAIL_QUEUE);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        connection.start();
        return messageConsumer;
    }

    public static void initCredentialsService(JsonParser jsonParser) throws IOException {
        File file = new File("./target/credentials.json");
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
        Credentials credentials = jsonParser.parse(FileUtils.readFileToString(file));
        CredentialsService credentialsService = CredentialsService.SERVICE;
        credentialsService.setCredentials(credentials);
        file.delete();
    }
}
