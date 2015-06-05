package com.quifers.servlet.listener;

import com.quifers.domain.enums.EmailType;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class WebPublisher {

    private final Session session;
    private final MessageProducer producer;

    public WebPublisher(Session session, MessageProducer producer) {
        this.session = session;
        this.producer = producer;
    }

    public void publishEmailMessage(EmailType emailType, String orderId) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setStringProperty("EMAIL_TYPE", emailType.name());
        objectMessage.setStringProperty("ORDER_ID", orderId);
        producer.send(objectMessage);
    }
}
