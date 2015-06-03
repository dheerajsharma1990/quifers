package com.quifers.email.helpers;

import com.quifers.dao.OrderDao;
import com.quifers.dao.PriceDao;
import com.quifers.domain.enums.EmailType;

public class EmailCreatorFactory {

    private final OrderDao orderDao;
    private final PriceDao priceDao;

    public EmailCreatorFactory(OrderDao orderDao, PriceDao priceDao) {
        this.orderDao = orderDao;
        this.priceDao = priceDao;
    }

    public EmailCreator getEmailCreator(EmailType emailType) {
        if (EmailType.ORDER.equals(emailType)) {
            return new OrderEmailCreator(orderDao);
        } else if (EmailType.PRICE.equals(emailType)) {
            return new PriceEmailCreator(orderDao, priceDao);
        }
        throw new IllegalArgumentException("No email of type: " + emailType);
    }

}
