package com.quifers.email.jms;

import com.quifers.email.properties.EmailUtilProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class EmailMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailMessageConsumer.class);
    private final Connection connection;
    private final Session session;
    private final Queue queue;

    public EmailMessageConsumer(EmailUtilProperties properties) throws JMSException {
        LOGGER.info("Connecting to ActiveMq...");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getActiveMqUrl());
        connection = connectionFactory.createConnection();
        connection.setClientID("EMAIL.ACTIVEMQ.CLIENT");
        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        queue = session.createQueue(properties.getEmailQueueName());
        connection.start();
    }

    public MessageConsumer getMessageConsumer() throws JMSException {
        return session.createConsumer(queue);
    }

    public void close() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
