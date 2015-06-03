package com.quifers.email.helpers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface EmailCreator {
    MimeMessage createEmail(Object object,String fromAddress) throws UnsupportedEncodingException, MessagingException;
}
