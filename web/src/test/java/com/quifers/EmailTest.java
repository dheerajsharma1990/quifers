package com.quifers;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailTest {
    public static void main(String[] args) {
        final String SMTP_HOST_NAME = "smtpout.europe.secureserver.net";
        final int SMTP_HOST_PORT = 465;
        String SMTP_AUTH_USER = "dheeraj.sharma@quifers.com";
        String SMTP_AUTH_PWD = "";

        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", SMTP_HOST_NAME);
            props.put("mail.smtps.auth", "true");

            Session mailSession = Session.getDefaultInstance(props);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSentDate(new Date());
            message.setSubject("Thanks For Placing Order With Quifers.OrderId: QID0004305");
            message.setContent("<H1>Hey Man,<br>Thanks for this.</H1>", "text/html");
            InternetAddress from = new InternetAddress("orders@quifers.com","Team Quifers");
            message.setFrom(from);
            InternetAddress address = new InternetAddress("dheerajsharma1990@gmail.com");
            message.addRecipient(Message.RecipientType.TO, address); //Send email To (Type email ID that you want to send)
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
