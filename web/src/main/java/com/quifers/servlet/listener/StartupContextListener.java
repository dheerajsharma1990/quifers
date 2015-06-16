package com.quifers.servlet.listener;

import com.quifers.hibernate.DaoFactory;
import com.quifers.Environment;
import com.quifers.authentication.AccessTokenGenerator;
import com.quifers.authentication.AdminAuthenticator;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.hibernate.DaoFactoryBuilder;
import com.quifers.properties.PropertiesLoader;
import com.quifers.properties.WebProperties;
import com.quifers.request.validators.AdminAccountRegisterRequestValidator;
import com.quifers.request.validators.AuthenticationRequestValidator;
import com.quifers.request.validators.OrderBookRequestValidator;
import com.quifers.request.validators.admin.AdminRegisterRequestValidator;
import com.quifers.service.OrderIdGeneratorService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class StartupContextListener implements ServletContextListener {

    public static final String ORDER_ID_SERVICE = "ORDER_ID_SERVICE";
    public static final String ORDER_BOOK_REQUEST_VALIDATOR = "ORDER_BOOK_REQUEST_VALIDATOR";
    public static final String ADMIN_ACCOUNT_REQUEST_VALIDATOR = "ADMIN_ACCOUNT_REQUEST_VALIDATOR";
    public static final String ADMIN_REQUEST_VALIDATOR = "ADMIN_REQUEST_VALIDATOR";
    public static final String ADMIN_AUTHENTICATOR = "ADMIN_AUTHENTICATOR";
    public static final String FIELD_EXECUTIVE_AUTHENTICATOR = "FIELD_EXECUTIVE_AUTHENTICATOR";
    public static final String ADMIN_TOKEN_GENERATOR = "ADMIN_TOKEN_GENERATOR";
    public static final String AUTHENTICATION_REQUEST_VALIDATOR = "AUTHENTICATION_REQUEST_VALIDATOR";

    public static final String ADMIN_DAO = "ADMIN_DAO";
    public static final String FIELD_EXECUTIVE_DAO = "FIELD_EXECUTIVE_DAO";
    public static final String ORDER_DAO = "ORDER_DAO";

    public static final String WEB_PUBLISHER = "WEB_PUBLISHER";


    private static final Logger LOGGER = LoggerFactory.getLogger(StartupContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Starting quifers webapp...");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Environment environment = getEnvironment(servletContext);
        WebProperties webProperties = loadWebProperties(environment);
        initDaos(servletContext, environment);
        OrderIdGeneratorService service = initialiseOrderIdService(servletContext, webProperties);
        initialiseActiveMqPublisher(servletContext);
        initialiseDao(servletContext);
        initialiseValidators(servletContext, service);
    }

    private WebProperties loadWebProperties(Environment environment) {
        try {
            return PropertiesLoader.loadProperties(environment);
        } catch (IOException e) {
            LOGGER.error("Unable to load properties for environment{}:{}", environment, e);
        }
        return null;
    }

    private Environment getEnvironment(ServletContext servletContext) {
        String env = servletContext.getInitParameter("env");
        if (env == null) {
            throw new IllegalArgumentException("No environment specified for running application.Available environments " + Environment.values().toString());
        }
        Environment environment;
        try {
            environment = Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("No such Environment [" + env + "] present in Environment enum.", exception);
        }
        return environment;
    }

    public OrderIdGeneratorService initialiseOrderIdService(ServletContext servletContext, WebProperties webProperties) {
        OrderIdGeneratorService service = new OrderIdGeneratorService(webProperties.getLastOrderIdCounter());
        servletContext.setAttribute(ORDER_ID_SERVICE, service);
        return service;
    }

    public void initialiseDao(ServletContext servletContext) {
        servletContext.setAttribute(ADMIN_ACCOUNT_REQUEST_VALIDATOR, new AdminAccountRegisterRequestValidator());
        servletContext.setAttribute(ADMIN_REQUEST_VALIDATOR, new AdminRegisterRequestValidator());
        servletContext.setAttribute(ADMIN_TOKEN_GENERATOR, new AccessTokenGenerator());
        servletContext.setAttribute(AUTHENTICATION_REQUEST_VALIDATOR, new AuthenticationRequestValidator());
    }

    private void initialiseValidators(ServletContext servletContext, OrderIdGeneratorService service) {
        servletContext.setAttribute(ORDER_BOOK_REQUEST_VALIDATOR, new OrderBookRequestValidator(service));
    }


    private void initialiseActiveMqPublisher(ServletContext servletContext) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue("QUIFERS.EMAIL.QUEUE");
            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            connection.start();
            servletContext.setAttribute(WEB_PUBLISHER, new WebPublisher(session, producer));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void initDaos(ServletContext servletContext, Environment environment) {
        try {
            DaoFactory daoFactory = DaoFactoryBuilder.getDaoFactory(environment);
            servletContext.setAttribute(ADMIN_DAO, daoFactory.getAdminDao());
            servletContext.setAttribute(FIELD_EXECUTIVE_DAO, daoFactory.getFieldExecutiveDao());
            servletContext.setAttribute(ADMIN_AUTHENTICATOR, new AdminAuthenticator(daoFactory.getAdminDao()));
            servletContext.setAttribute(FIELD_EXECUTIVE_AUTHENTICATOR, new FieldExecutiveAuthenticator(daoFactory.getFieldExecutiveDao()));
            servletContext.setAttribute(ORDER_DAO, daoFactory.getOrderDao());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Stopping quifers webapp...");
    }
}
