package com.quifers.email;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.*;
import com.quifers.email.jms.EmailMessageConsumer;
import com.quifers.email.jms.OrderReceiver;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.CredentialsService;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Timer;

import static com.quifers.email.properties.PropertiesLoader.loadEmailUtilProperties;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) {
        LOGGER.info("Starting quifers email service...");
        DaoFactory daoFactory = null;
        EmailMessageConsumer messageConsumer = null;
        try {
            Environment environment = getEnvironment();
            EmailUtilProperties properties = loadEmailUtilProperties(environment);
            daoFactory = DaoFactoryBuilder.getDaoFactory(environment);
            CredentialsService credentialsService = new CredentialsService(CredentialsService.DEFAULT_DIR, new JsonParser());
            scheduleCredentialsRefreshingTask(credentialsService, properties);

            messageConsumer = new EmailMessageConsumer(properties);
            OrderReceiver orderReceiver = getOrderReceiver(properties, messageConsumer.getMessageConsumer(), daoFactory, credentialsService);
            receiveOrders(orderReceiver);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (daoFactory != null) {
                daoFactory.closeDaoFactory();
            }
            if (messageConsumer != null) {
                messageConsumer.close();
            }
        }

    }

    private static void receiveOrders(OrderReceiver orderReceiver) throws JMSException, MessagingException, IOException {
        while (true) {
            orderReceiver.receiveOrders();
        }
    }

    private static Environment getEnvironment() throws Exception {
        String env = System.getProperty("env");
        if (env == null) {
            throw new Exception("No environment specified.Kindly look at the Environment enum for possible values.");
        }
        try {
            return Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("No such environment exists [" + env + "].");
        }
    }

    private static OrderReceiver getOrderReceiver(EmailUtilProperties properties, MessageConsumer messageConsumer, DaoFactory daoFactory, CredentialsService credentialsService) throws JMSException, IOException, MessagingException {
        OrderDao orderDao = daoFactory.getOrderDao();
        EmailHttpRequestSender emailHttpRequestSender = new EmailHttpRequestSender(new HttpRequestSender());
        EmailRequestBuilder builder = new EmailRequestBuilder();
        EmailSender emailSender = new EmailSender(emailHttpRequestSender, builder);
        return new OrderReceiver(properties, messageConsumer, emailSender, new EmailCreatorFactory(orderDao), credentialsService);
    }

    private static void scheduleCredentialsRefreshingTask(CredentialsService credentialsService, EmailUtilProperties properties) {
        LOGGER.info("Scheduling credential refresher task with delay of {} milliseconds...", properties.getCredentialsRefreshDelayInSeconds());
        CredentialsRefresher credentialsRefresher = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(properties), jsonParser);
        CredentialsRefresherTask refresherTask = new CredentialsRefresherTask(credentialsService, credentialsRefresher);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(refresherTask, 0, properties.getCredentialsRefreshDelayInSeconds() * 1000);
    }

}
