package com.quifers.email.helpers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PriceEmailCreator implements EmailCreator {

    private final OrderDao orderDao;

    public PriceEmailCreator(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public MimeMessage createEmail(String orderId, String fromAddress) throws UnsupportedEncodingException, MessagingException {
        Order order = orderDao.getOrder(orderId);
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage emailMessage = new MimeMessage(session);

        emailMessage.setFrom(getFromAddress(fromAddress));
        emailMessage.addRecipient(Message.RecipientType.TO, getToAddress(order.getClient().getEmail()));
        emailMessage.setSubject(getSubject(order));
        emailMessage.setContent(getBody(order), "text/html");
        emailMessage.addHeader("Content-Type", "text/html");

        return emailMessage;
    }

    private String getBody(Order order) {
        return "<html>\n" +
                "<body>\n" +
                "Hello ," +
                "<br>\n" +
                "We are pleased to have your order served by quifers.Below are the details.<br>\n" +
                "<table rules=\"all\" style=\"border-color: #666;\" cellpadding=\"10\">" +
                "<tr><td><strong>Order Id :</strong> </td><td>" + order.getOrderId() + "</td></tr>" +
                "<tr><td><strong>Waiting Cost :</strong> </td><td>" + order.getWaitingCost() + "</td></tr>" +
                "<tr><td><strong>Transit Cost :</strong> </td><td>" + order.getTransitCost() + "</td></tr>" +
                "<tr><td><strong>Labour Cost  :</strong> </td><td>" + order.getLabourCost() + "</td></tr>" +
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
        return "Quifers Bill .Order Id [" + order.getOrderId() + "]";
    }

}
