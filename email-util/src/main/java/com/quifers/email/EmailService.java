package com.quifers.email;

import com.quifers.Environment;
import com.quifers.dao.OrderDao;
import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.CredentialsRefresher;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailHttpRequestSender;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.jms.EmailMessageConsumer;
import com.quifers.email.jms.OrderReceiver;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;

import static com.quifers.email.properties.PropertiesLoader.loadEmailUtilProperties;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) throws Exception {
        Environment environment = getEnvironment();
        loadLog4jProperties(environment);
        EmailUtilProperties emailUtilProperties = loadEmailUtilProperties(environment);
        DaoFactory daoFactory = DaoFactoryBuilder.getDaoFactory(environment);
        startEmailService(emailUtilProperties, daoFactory);
    }

    private static void startEmailService(EmailUtilProperties emailUtilProperties, DaoFactory daoFactory) {
        LOGGER.info("Starting quifers email service...");
        EmailMessageConsumer messageConsumer = null;
        try {
            CredentialsRefresher credentialsRefresher = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(emailUtilProperties), jsonParser, emailUtilProperties.getRefreshToken());
            messageConsumer = new EmailMessageConsumer(emailUtilProperties);
            OrderReceiver orderReceiver = getOrderReceiver(emailUtilProperties, messageConsumer.getMessageConsumer(), credentialsRefresher, daoFactory.getOrderDao());
            receiveOrders(orderReceiver);
        } catch (Throwable e) {
            LOGGER.error("It's all over.Something terrible has happened.Email Service Is Shutting Down..{}", e);
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
            throw new IllegalArgumentException("No environment specified.Kindly look at the Environment enum for possible values.");
        }
        try {
            return Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No such environment exists.", e);
        }
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream("properties/" + environment.name().toLowerCase() + "/log4j.properties");
        PropertyConfigurator.configure(inputStream);
    }

    private static OrderReceiver getOrderReceiver(EmailUtilProperties properties, MessageConsumer messageConsumer, CredentialsRefresher credentialsRefresher, OrderDao orderDao) throws JMSException, IOException, MessagingException {
        EmailHttpRequestSender emailHttpRequestSender = new EmailHttpRequestSender(new HttpRequestSender());
        EmailRequestBuilder builder = new EmailRequestBuilder();
        EmailSender emailSender = new EmailSender(emailHttpRequestSender, builder);
        return new OrderReceiver(messageConsumer, emailSender, new EmailCreatorFactory(properties.getEmailAccount()), credentialsRefresher, orderDao);
    }


}
