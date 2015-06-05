package com.quifers.email.helpers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.enums.EmailType;

public class EmailCreatorFactory {

    private final OrderDao orderDao;

    public EmailCreatorFactory(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public EmailCreator getEmailCreator(EmailType emailType) {
        if (EmailType.ORDER.equals(emailType)) {
            return new OrderEmailCreator(orderDao);
        } else if (EmailType.PRICE.equals(emailType)) {
            return new PriceEmailCreator(orderDao);
        }
        throw new IllegalArgumentException("No email of type: " + emailType);
    }

}
