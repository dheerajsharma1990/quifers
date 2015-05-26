package com.quifers.email.helpers;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailCreator {

    public MimeMessage createEmail(Order order, String fromAddress) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage emailMessage = new MimeMessage(session);

        emailMessage.setFrom(getFromAddress(fromAddress));
        emailMessage.addRecipient(Message.RecipientType.TO, getToAddress(order));
        emailMessage.setSubject(getSubject(order));
        emailMessage.setContent(getBody(order), "text/html");
        emailMessage.addHeader("Content-Type", "text/html");

        return emailMessage;
    }

    private String getBody(Order order) {
        OrderWorkflow workflow = order.getOrderWorkflows().iterator().next();
        return "<html>\n" +
                "<body>\n" +
                "Hey " + order.getName() + ",\n" +
                "<br>\n" +
                "Thanks for placing order with Quifers.Below are your order details.<br>\n" +
                "Order Id: " + order.getOrderId() + "<br>\n" +
                "Name: " + order.getName() + "<br>\n" +
                "From: " + order.getFromAddress() + "<br>\n" +
                "To: " + order.getToAddress() + "<br>\n" +
                "Booking Time: " + workflow.getEffectiveTime() + "<br>\n" +
                "Order State: " + workflow.getOrderState() + "<br>\n" +
                "Thanks\n" +
                "<strong>Team Quifers</strong>\n" +
                "</body>\n" +
                "</html>";
    }

    private InternetAddress getFromAddress(String fromAddress) throws UnsupportedEncodingException {
        return new InternetAddress(fromAddress, "Team Quifers");
    }

    private InternetAddress getToAddress(Order order) throws AddressException {
        return new InternetAddress(order.getEmail());
    }

    private String getSubject(Order order) {
        return "Thanks For Placing Order With Quifers.Order Id: " + order.getOrderId();
    }

}
