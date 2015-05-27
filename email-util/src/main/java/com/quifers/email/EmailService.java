package com.quifers.email;

import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
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
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) throws JMSException, IOException, MessagingException {
        LOGGER.info("Starting quifers email service... ");
        EmailUtilProperties properties = loadEmailUtilProperties();

        initCredentialsService(jsonParser);
        CredentialsRefresher credentialsRefresher = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(properties), jsonParser);
        startCredentialsRefreshingTask(credentialsRefresher, properties.getCredentialsRefreshDelayInSeconds());

        MessageConsumer messageConsumer = startActiveMq(properties);
        receiveOrders(properties, messageConsumer);
    }

    private static EmailUtilProperties loadEmailUtilProperties() throws IOException {
        Environment environment = Environment.valueOf(System.getProperty("env"));
        return new PropertiesLoader().getEmailUtilProperties(environment);
    }

    private static void receiveOrders(EmailUtilProperties properties, MessageConsumer messageConsumer) throws JMSException, IOException, MessagingException {
        EmailSender emailSender = new EmailSender(new EmailCreator());
        OrderReceiver orderReceiver = new OrderReceiver(properties, messageConsumer, emailSender, CredentialsService.SERVICE);
        orderReceiver.receiveOrders();
    }

    private static void startCredentialsRefreshingTask(CredentialsRefresher credentialsRefresher, int delay) {
        LOGGER.info("Scheduling credential refresher task with delay of {} milliseconds...");
        CredentialsRefresherTask refresherTask = new CredentialsRefresherTask(credentialsRefresher);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(refresherTask, 0, delay * 1000);
    }

    private static MessageConsumer startActiveMq(EmailUtilProperties properties) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getActiveMqUrl());
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(properties.getEmailQueueName());
        MessageConsumer messageConsumer = session.createConsumer(queue);
        connection.start();
        LOGGER.info("Started listening for orders on active-mq...");
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
        LOGGER.info("Successfully loaded api credentials.");
    }
}
