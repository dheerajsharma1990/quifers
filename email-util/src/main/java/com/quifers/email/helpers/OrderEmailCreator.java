package com.quifers.email.helpers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class OrderEmailCreator implements EmailCreator {

    private final OrderDao orderDao;

    public OrderEmailCreator(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public MimeMessage createEmail(String orderId, String fromAddress) throws UnsupportedEncodingException, MessagingException {
        Order order = orderDao.getOrder(orderId);
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
        OrderWorkflow workflow = order.getWorkflow(OrderState.BOOKED);
        return "<html>\n" +
                "<body>\n" +
                "Hello " + order.getClient().getName() + ",\n" +
                "<br>\n" +
                "We are pleased to have you on-board, Below are the details of your booking that has been confirmed with us.<br>\n" +
                "<table rules=\"all\" style=\"border-color: #666;\" cellpadding=\"10\">" +
                "<tr><td><strong>Order Id :</strong> </td><td>" + order.getOrderId() + "</td></tr>" +
                "<tr><td><strong>Name :</strong> </td><td>" + order.getClient().getName() + "</td></tr>" +
                "<tr><td><strong>Pick-Up Address :</strong> </td><td>" + order.getPickUpAddress().getAddressHouseNumber() + "," + order.getPickUpAddress().getAddressSociety() + "," + order.getPickUpAddress().getAddressArea() + "," + order.getPickUpAddress().getAddressCity() + "</td></tr>" +
                "<tr><td><strong>Drop-Off Address :</strong> </td><td>" + order.getDropOffAddress().getAddressHouseNumber() + "," + order.getDropOffAddress().getAddressSociety() + "," + order.getDropOffAddress().getAddressArea() + "," + order.getDropOffAddress().getAddressCity() + "</td></tr>" +
                "<tr><td><strong>Booking Time :</strong> </td><td>" + workflow.getEffectiveTime() + "</td></tr>" +
                "<tr><td><strong>Order State :</strong> </td><td>" + workflow.getOrderState() + "</td></tr>" +
                "</table>" +
                "<br/>Please note that loading & unloading is free for first <b>60 mins</b><br/>" +
                "Extra waiting time to be charged at INR 50 for every 15 min.<br/>" +
                "(Waiting Time = Total Engagement Time - Transit Time, Engagement Time starts as soon as the driver reports at the pick-up location)<br/><br/>" +
                "<br/>For any kind of feedback or queries or complaints or cancellations, Please get back to us at +91-7709365614, 9987128614, 7506035366." +
                "<br/>The Driver shall contact you well in advance to schedule your moving." +
                "Wishing You a happy experience.<br/>Regards,<br/><strong>Team Quifers<strong>" +
                "</body>\n" +
                "</html>";
    }

    private InternetAddress getFromAddress(String fromAddress) throws UnsupportedEncodingException {
        return new InternetAddress(fromAddress, "Team Quifers");
    }

    private InternetAddress getToAddress(Order order) throws AddressException {
        return new InternetAddress(order.getClient().getEmail());
    }

    private String getSubject(Order order) {
        return "Confirmation for your Quifers Booking.Order Id [" + order.getOrderId() + "]";
    }

}
