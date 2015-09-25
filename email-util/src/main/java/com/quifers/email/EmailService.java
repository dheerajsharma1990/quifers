package com.quifers.email;

import com.quifers.Environment;
import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.helpers.CredentialsRefresher;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailHttpRequestSender;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.jms.EmailMessageListener;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.io.InputStream;

import static com.quifers.email.properties.PropertiesLoader.loadEmailUtilProperties;

public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static JsonParser jsonParser = new JsonParser();

    public static void main(String[] args) throws Exception {
        Environment environment = Environment.getEnvironment(System.getProperty("env"));
        loadLog4jProperties(environment);
        EmailUtilProperties emailUtilProperties = loadEmailUtilProperties(environment);
        startEmailService(emailUtilProperties);
    }

    private static void startEmailService(EmailUtilProperties emailUtilProperties) {
        LOGGER.info("Starting quifers email service...");
        try {
            CredentialsRefresher credentialsRefresher = new CredentialsRefresher(new HttpRequestSender(), new AccessTokenRefreshRequestBuilder(emailUtilProperties), jsonParser, emailUtilProperties.getRefreshToken());
            EmailHttpRequestSender emailHttpRequestSender = new EmailHttpRequestSender(new HttpRequestSender());
            EmailRequestBuilder builder = new EmailRequestBuilder();
            EmailSender emailSender = new EmailSender(emailHttpRequestSender, builder);
            EmailMessageListener emailMessageListener = new EmailMessageListener(emailSender, new EmailCreatorFactory(emailUtilProperties.getEmailAccount()), credentialsRefresher);
            registerMessageListener(emailUtilProperties, emailMessageListener);
        } catch (Throwable e) {
            LOGGER.error("It's all over.Something terrible has happened.Email Service Is Shutting Down..{}", e);
        }
    }

    private static void registerMessageListener(EmailUtilProperties properties, EmailMessageListener emailMessageListener) throws JMSException {
        LOGGER.info("Connecting to ActiveMq...");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getActiveMqUrl());
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("EMAIL.ACTIVEMQ.CLIENT");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(properties.getEmailQueueName()));
        consumer.setMessageListener(emailMessageListener);
        connection.start();
        LOGGER.info("Connected..");
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = EmailService.class.getClassLoader().getResourceAsStream(environment.getPropertiesFilePath("log4j.properties"));
        PropertyConfigurator.configure(inputStream);

    }

}
