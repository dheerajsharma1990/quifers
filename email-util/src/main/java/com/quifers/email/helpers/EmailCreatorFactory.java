package com.quifers.email.helpers;

public class EmailCreatorFactory {

    public static EmailCreator getEmailCreator(EmailType emailType) {
        if (EmailType.ORDER.equals(emailType)) {
            return new OrderEmailCreator();
        } else if (EmailType.PRICE.equals(emailType)) {
            return new PriceEmailCreator();
        }
        throw new IllegalArgumentException("No email of type: " + emailType);
    }

}
