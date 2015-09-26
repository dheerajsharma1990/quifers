package com.quifers.servlet.listener;

import com.quifers.Environment;
import com.quifers.hibernate.DaoFactory;
import com.quifers.hibernate.DaoFactoryBuilder;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupContextListener.class);

    public static final String DAO_FACTORY = "DAO_FACTORY";
    public static final String ORDER_ID_SERVICE = "ORDER_ID_SERVICE";
    public static final String WEB_PUBLISHER = "WEB_PUBLISHER";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Starting quifers webapp...");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Environment environment = Environment.getEnvironment(servletContext.getInitParameter("env"));
        initialiseDaoFactory(servletContext, environment);
        initialiseOrderIdService(servletContext);
        initialiseActiveMqPublisher(servletContext);
    }


    private void initialiseDaoFactory(ServletContext servletContext, Environment environment) {
        try {
            DaoFactory daoFactory = DaoFactoryBuilder.getDaoFactory(environment);
            servletContext.setAttribute(DAO_FACTORY, daoFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OrderIdGeneratorService initialiseOrderIdService(ServletContext servletContext) {
        String lastOrderIdCounter = servletContext.getInitParameter("lastOrderIdCounter");
        if (lastOrderIdCounter == null) {
            throw new IllegalArgumentException("No lastOrderIdCounter parameter specified.");
        }
        OrderIdGeneratorService service = new OrderIdGeneratorService(Long.valueOf(lastOrderIdCounter));
        servletContext.setAttribute(ORDER_ID_SERVICE, service);
        return service;
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


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Stopping quifers webapp...");
    }
}
