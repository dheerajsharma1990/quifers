package com.quifers.email.helpers;

import com.quifers.domain.Cost;
import com.quifers.domain.Order;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class BillDetailsEmailCreator implements EmailCreator {

    private final String fromAddress;

    public BillDetailsEmailCreator(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    @Override
    public MimeMessage createEmail(Order order) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage emailMessage = new MimeMessage(session);

        emailMessage.setFrom(getFromAddress(fromAddress));
        emailMessage.addRecipient(Message.RecipientType.TO, getToAddress(order.getClient().getEmail()));
        emailMessage.setSubject(getSubject(order));
        emailMessage.setContent(getBody(order.getCost()), "text/html");
        emailMessage.addHeader("Content-Type", "text/html");

        return emailMessage;
    }

    private String getBody(Cost cost) {
        return "<html>\n" +
                "<body>\n" +
                "Hello ," +
                "<br>\n" +
                "We are pleased to have your order served by quifers.Below are the details.<br>\n" +
                "<table rules=\"all\" style=\"border-color: #666;\" cellpadding=\"10\">" +
                "<tr><td><strong>Order Id :</strong> </td><td>" + cost.getOrderId().getOrderId() + "</td></tr>" +
                "<tr><td><strong>Waiting Cost :</strong> </td><td>" + cost.getWaitingCost() + "</td></tr>" +
                "<tr><td><strong>Transit Cost :</strong> </td><td>" + cost.getTransitCost() + "</td></tr>" +
                "<tr><td><strong>Labour Cost  :</strong> </td><td>" + cost.getLabourCost() + "</td></tr>" +
                "<tr><td><strong>Total Cost  :</strong> </td><td>" + cost.getTotalCost() + "</td></tr>" +
                "</table>" +
                "<br/>Please note that loading & unloading is free for first <b>60 mins</b><br/>" +
                "Extra waiting time has been charged at INR 50 for every 15 min.<br/>" +
                "(Waiting Time = Total Engagement Time - Transit Time, Engagement Time starts as soon as the driver reports at the pick-up location)<br/><br/>" +
                "<br/>For any kind of feedback or queries or complaints, Please get back to us at +91-7709365614, 9987128614, 7506035366." +
                "<br/>Regards,<br/><strong>Team Quifers<strong>" +
                "</body>\n" +
                "</html>";
    }

    private InternetAddress getFromAddress(String fromAddress) throws UnsupportedEncodingException {
        return new InternetAddress(fromAddress, "Team Quifers");
    }

    private InternetAddress getToAddress(String address) throws AddressException {
        return new InternetAddress(address);
    }

    private String getSubject(Order order) {
        return "Quifers Bill .Order Id [" + order.getOrderId().getOrderId() + "]";
    }

}
