package com.quifers.email.jms;

import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailSender;
import com.quifers.domain.enums.EmailType;
import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.CredentialsService;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.mail.MessagingException;
import java.io.IOException;

public class OrderReceiver {

    private final EmailUtilProperties properties;
    private final MessageConsumer messageConsumer;
    private final EmailSender emailSender;
    private final CredentialsService credentialsService;
    private final EmailCreatorFactory emailCreatorFactory;

    public OrderReceiver(EmailUtilProperties properties, MessageConsumer messageConsumer, EmailSender emailSender, CredentialsService credentialsService, EmailCreatorFactory emailCreatorFactory) throws JMSException {
        this.properties = properties;
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.credentialsService = credentialsService;
        this.emailCreatorFactory = emailCreatorFactory;
    }

    public void receiveOrders() throws JMSException, IOException, MessagingException {
        while (true) {
            ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
            String emailType = message.getStringProperty("EMAIL_TYPE");
            long orderId = message.getLongProperty("ORDER_ID");
            EmailType type = EmailType.valueOf(emailType);
            emailOrder(credentialsService.getCredentials(), emailCreatorFactory.getEmailCreator(type), orderId);
            message.acknowledge();
        }
    }

    private void emailOrder(Credentials credentials, EmailCreator emailCreator, long orderId) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, emailCreator, orderId, properties.getEmailAccount());
    }

}
