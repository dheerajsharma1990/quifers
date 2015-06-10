package com.quifers.email.helpers;

import com.quifers.domain.enums.EmailType;

public class EmailCreatorFactory {

    private final String fromAddress;

    public EmailCreatorFactory(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public EmailCreator getEmailCreator(EmailType emailType) {
        if (EmailType.NEW_ORDER.equals(emailType)) {
            return new NewOrderEmailCreator(fromAddress);
        } else if (EmailType.BILL_DETAILS.equals(emailType)) {
            return new BillDetailsEmailCreator(fromAddress);
        }
        throw new IllegalArgumentException("No email of type: " + emailType);
    }

}
