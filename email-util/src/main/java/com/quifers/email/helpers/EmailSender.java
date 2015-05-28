package com.quifers.email.helpers;

import com.quifers.domain.Order;
import com.quifers.email.builders.EmailRequestBuilder;
import com.quifers.email.util.Credentials;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class EmailSender {

    private final EmailHttpRequestSender emailHttpRequestSender;
    private final EmailRequestBuilder builder;
    private final EmailCreator emailCreator;

    public EmailSender(EmailHttpRequestSender emailHttpRequestSender, EmailRequestBuilder builder, EmailCreator emailCreator) {
        this.emailHttpRequestSender = emailHttpRequestSender;
        this.builder = builder;
        this.emailCreator = emailCreator;
    }

    public String sendEmail(Credentials credentials, Order order, String fromAddress) throws IOException, MessagingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MimeMessage mimeMessage = emailCreator.createEmail(order, fromAddress);
        mimeMessage.writeTo(out);
        byte[] binaryData = out.toByteArray();
        String encode = encodeBase64URLSafeString(binaryData);
        String request = builder.buildEmailRequest(encode);
        return emailHttpRequestSender.sendEmailHttpRequest(credentials, request);
    }

}
