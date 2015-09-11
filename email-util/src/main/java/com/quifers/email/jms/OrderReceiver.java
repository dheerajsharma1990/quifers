package com.quifers.email.jms;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.id.OrderId;
import com.quifers.email.helpers.CredentialsRefresher;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.util.Credentials;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;

public class OrderReceiver {

    private final MessageConsumer messageConsumer;
    private final EmailSender emailSender;
    private final EmailCreatorFactory emailCreatorFactory;
    private final CredentialsRefresher credentialsRefresher;
    private final OrderDao orderDao;

    public OrderReceiver(MessageConsumer messageConsumer, EmailSender emailSender, EmailCreatorFactory emailCreatorFactory, CredentialsRefresher credentialsRefresher, OrderDao orderDao) throws JMSException {
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.emailCreatorFactory = emailCreatorFactory;
        this.credentialsRefresher = credentialsRefresher;
        this.orderDao = orderDao;
    }

    public void receiveOrders() throws JMSException, IOException, MessagingException {
        ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
        String emailType = message.getStringProperty("EMAIL_TYPE");
        String orderId = message.getStringProperty("ORDER_ID");
        EmailType type = EmailType.valueOf(emailType);
        Order order = orderDao.getOrder(new OrderId(orderId));
        emailOrder(credentialsRefresher.getRefreshedCredentials(), emailCreatorFactory.getEmailCreator(type), order);
        message.acknowledge();
    }

    private void emailOrder(Credentials credentials, EmailCreator emailCreator, Order order) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, emailCreator, order);
    }

}
