package com.quifers.email.jms;

import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.email.helpers.CredentialsRefresher;
import com.quifers.email.helpers.EmailCreatorFactory;
import com.quifers.email.helpers.EmailSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import java.io.IOException;

public class EmailMessageListener implements MessageListener {

    private final EmailSender emailSender;
    private final EmailCreatorFactory emailCreatorFactory;
    private final CredentialsRefresher credentialsRefresher;

    public EmailMessageListener(EmailSender emailSender, EmailCreatorFactory emailCreatorFactory, CredentialsRefresher credentialsRefresher) throws JMSException {
        this.emailSender = emailSender;
        this.emailCreatorFactory = emailCreatorFactory;
        this.credentialsRefresher = credentialsRefresher;
    }

    @Override
    public void onMessage(Message message) {
        try {
            Order order = (Order) ((ObjectMessage) message).getObject();
            String emailType = message.getStringProperty("EMAIL_TYPE");
            EmailType type = EmailType.valueOf(emailType);
            emailSender.sendEmail(credentialsRefresher.getRefreshedCredentials(), emailCreatorFactory.getEmailCreator(type), order);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
