package com.quifers.email.jms;

import com.quifers.domain.Order;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;

public class OrderReceiver {

    private final MessageConsumer messageConsumer;
    private final EmailSender emailSender;
    private final CredentialsService credentialsService;

    public OrderReceiver(MessageConsumer messageConsumer, EmailSender emailSender, CredentialsService credentialsService) throws JMSException {
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.credentialsService = credentialsService;
    }

    public void listenForOrders() throws JMSException, IOException, MessagingException {
        while (true) {
            ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
            Order order = (Order) message.getObject();
            emailOrder(credentialsService.getCredentials(), order);
            message.acknowledge();
        }
    }

    private void emailOrder(Credentials credentials, Order order) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, order, "dheeraj.sharma.aws@gmail.com");
    }

}
