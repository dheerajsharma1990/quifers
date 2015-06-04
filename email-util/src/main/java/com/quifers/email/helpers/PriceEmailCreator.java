package com.quifers.email.helpers;

import com.quifers.dao.OrderDao;
import com.quifers.dao.PriceDao;
import com.quifers.domain.Order;
import com.quifers.domain.Price;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PriceEmailCreator implements EmailCreator {

    private final PriceDao priceDao;
    private final OrderDao orderDao;

    public PriceEmailCreator(OrderDao orderDao, PriceDao priceDao) {
        this.orderDao = orderDao;
        this.priceDao = priceDao;
    }

    @Override
    public MimeMessage createEmail(long orderId, String fromAddress) throws UnsupportedEncodingException, MessagingException {
        Order order = orderDao.getOrder(orderId);
        Price price = priceDao.getPrice(orderId);
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage emailMessage = new MimeMessage(session);

        emailMessage.setFrom(getFromAddress(fromAddress));
        emailMessage.addRecipient(Message.RecipientType.TO, getToAddress(order.getClient().getEmail()));
        emailMessage.setSubject(getSubject(price));
        emailMessage.setContent(getBody(price), "text/html");
        emailMessage.addHeader("Content-Type", "text/html");

        return emailMessage;
    }

    private String getBody(Price price) {
        return "<html>\n" +
                "<body>\n" +
                "Hello ," +
                "<br>\n" +
                "We are pleased to have your order served by quifers.Below are the details.<br>\n" +
                "<table rules=\"all\" style=\"border-color: #666;\" cellpadding=\"10\">" +
                "<tr><td><strong>Order Id :</strong> </td><td>" + price.getOrderId() + "</td></tr>" +
                "<tr><td><strong>Waiting Cost :</strong> </td><td>" + price.getWaitingCost() + "</td></tr>" +
                "<tr><td><strong>Transit Cost :</strong> </td><td>" + price.getTransitCost() + "</td></tr>" +
                "<tr><td><strong>Labour Cost  :</strong> </td><td>" + price.getLabourCost() + "</td></tr>" +
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

    private String getSubject(Price price) {
        return "Quifers Bill .Order Id [" + price.getOrderId() + "]";
    }

}
