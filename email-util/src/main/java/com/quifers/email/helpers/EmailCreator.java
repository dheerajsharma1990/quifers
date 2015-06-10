package com.quifers.email.helpers;

import com.quifers.domain.Order;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface EmailCreator {
    MimeMessage createEmail(Order order) throws UnsupportedEncodingException, MessagingException;
}
