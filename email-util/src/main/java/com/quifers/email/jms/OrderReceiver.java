package com.quifers.email.jms;

import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.helpers.EmailType;
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

    public OrderReceiver(EmailUtilProperties properties, MessageConsumer messageConsumer, EmailSender emailSender, CredentialsService credentialsService) throws JMSException {
        this.properties = properties;
        this.messageConsumer = messageConsumer;
        this.emailSender = emailSender;
        this.credentialsService = credentialsService;
    }

    public void receiveOrders() throws JMSException, IOException, MessagingException {
        while (true) {
            ActiveMQObjectMessage message = (ActiveMQObjectMessage) messageConsumer.receive();
            String emailType = message.getStringProperty("EMAIL_TYPE");
            EmailType type = EmailType.valueOf(emailType);
            emailOrder(credentialsService.getCredentials(), EmailCreatorFactory.getEmailCreator(type), message.getObject());
            message.acknowledge();
        }
    }

    private void emailOrder(Credentials credentials, EmailCreator emailCreator, Object object) throws IOException, MessagingException {
        emailSender.sendEmail(credentials, emailCreator, object, properties.getEmailAccount());
    }

}
