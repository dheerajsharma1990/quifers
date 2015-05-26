package com.quifers.email;

import com.quifers.email.jms.OrderListener;
import com.quifers.email.util.EmailCreator;
import com.quifers.email.util.EmailSender;

import javax.jms.JMSException;
import javax.mail.MessagingException;
import java.io.IOException;

public class EmailService {

    public static void main(String[] args) throws JMSException, IOException, MessagingException {
        OrderListener orderListener = new OrderListener(new EmailSender(), new EmailCreator());
        orderListener.listenForOrders();
    }
}
