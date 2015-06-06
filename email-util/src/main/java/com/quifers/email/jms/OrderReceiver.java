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
    private final EmailCreatorFactory emailCreatorFactory;

    public OrderReceiver(EmailUtilProperties properties, MessageConsumer messageConsumer, EmailSender emailSender, EmailCreatorFactory emailCreatorFactory) throws JMSException {
        this.properties = properties;
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.emailCreatorFactory = emailCreatorFactory;
    }

    public void receiveOrders() throws JMSException, IOException, MessagingException {
        while (true) {
            ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
            String emailType = message.getStringProperty("EMAIL_TYPE");
            String orderId = message.getStringProperty("ORDER_ID");
            EmailType type = EmailType.valueOf(emailType);
            emailOrder(CredentialsService.getCredentials(), emailCreatorFactory.getEmailCreator(type), orderId);
            message.acknowledge();
        }
    }

    private void emailOrder(Credentials credentials, EmailCreator emailCreator, String orderId) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, emailCreator, orderId, properties.getEmailAccount());
    }

}
