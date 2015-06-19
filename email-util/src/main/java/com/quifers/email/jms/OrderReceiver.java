package com.quifers.email.jms;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.id.OrderId;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;

public class OrderReceiver {

    private final MessageConsumer messageConsumer;
    private final EmailSender emailSender;
    private final EmailCreatorFactory emailCreatorFactory;
    private final CredentialsService credentialsService;
    private final OrderDao orderDao;

    public OrderReceiver(MessageConsumer messageConsumer, EmailSender emailSender, EmailCreatorFactory emailCreatorFactory, CredentialsService credentialsService, OrderDao orderDao) throws JMSException {
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.emailCreatorFactory = emailCreatorFactory;
        this.credentialsService = credentialsService;
        this.orderDao = orderDao;
    }

    public void receiveOrders() throws JMSException, IOException, MessagingException {
        ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
        String emailType = message.getStringProperty("EMAIL_TYPE");
        String orderId = message.getStringProperty("ORDER_ID");
        EmailType type = EmailType.valueOf(emailType);
        Order order = orderDao.getOrder(new OrderId(orderId));
        emailOrder(credentialsService.getCredentials(), emailCreatorFactory.getEmailCreator(type), order);
        message.acknowledge();
    }

    private void emailOrder(Credentials credentials, EmailCreator emailCreator, Order order) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, emailCreator, order);
    }

}
