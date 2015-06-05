package com.quifers.email;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.*;
import com.quifers.email.jms.OrderReceiver;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import com.quifers.hibernate.OrderDaoImpl;
import com.quifers.hibernate.SessionFactoryBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

import static com.quifers.email.properties.PropertiesLoader.loadEmailUtilProperties;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting quifers email service...");
        Environment environment = getEnvironment();
        EmailUtilProperties properties = loadEmailUtilProperties(environment);

        initialiseCredentialsService(jsonParser);

        CredentialsRefresher credentialsRefresher = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(properties), jsonParser);
        scheduleCredentialsRefreshingTask(credentialsRefresher, properties.getCredentialsRefreshDelayInSeconds());

        SessionFactory sessionFactory = SessionFactoryBuilder.getSessionFactory(environment);
        MessageConsumer messageConsumer = connectToActiveMq(properties);
        receiveOrders(properties, messageConsumer, sessionFactory);
    }

    private static Environment getEnvironment() throws Exception {
        String env = System.getProperty("env");
        if (env == null) {
            throw new Exception("No environment specified.Kindly look at the Environment enum for possible values.");
        }
        try {
            Environment environment = Environment.valueOf(env.toUpperCase());
            return environment;
        } catch (IllegalArgumentException e) {
            throw new Exception("No such environment exists [" + env + "].");
        }
    }

    private static void receiveOrders(EmailUtilProperties properties, MessageConsumer messageConsumer, SessionFactory sessionFactory) throws JMSException, IOException, MessagingException {
        OrderDao orderDao = new OrderDaoImpl(sessionFactory);
        EmailHttpRequestSender emailHttpRequestSender = new EmailHttpRequestSender(new HttpRequestSender());
        EmailRequestBuilder builder = new EmailRequestBuilder();
        EmailSender emailSender = new EmailSender(emailHttpRequestSender, builder);
        OrderReceiver orderReceiver = new OrderReceiver(properties, messageConsumer, emailSender, CredentialsService.SERVICE, new EmailCreatorFactory(orderDao));
        orderReceiver.receiveOrders();
    }

    private static void scheduleCredentialsRefreshingTask(CredentialsRefresher credentialsRefresher, int delay) {
        LOGGER.info("Scheduling credential refresher task with delay of {} milliseconds...");
        CredentialsRefresherTask refresherTask = new CredentialsRefresherTask(credentialsRefresher);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(refresherTask, 0, delay * 1000);
    }

    private static MessageConsumer connectToActiveMq(EmailUtilProperties properties) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getActiveMqUrl());
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(properties.getEmailQueueName());
        MessageConsumer messageConsumer = session.createConsumer(queue);
        connection.start();
        LOGGER.info("Started listening for orders on active-mq...");
        return messageConsumer;
    }

    public static void initialiseCredentialsService(JsonParser jsonParser) throws IOException {
        File file = new File("./target/credentials.json");
        if (!file.exists()) {
            throw new FileNotFoundException("Credentials file not present.Kindly generate it." + file.getName());
        }
        Credentials credentials = jsonParser.parse(FileUtils.readFileToString(file));
        CredentialsService credentialsService = CredentialsService.SERVICE;
        credentialsService.setCredentials(credentials);
        LOGGER.info("Successfully loaded api credentials.");
    }
}
