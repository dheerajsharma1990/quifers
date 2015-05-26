package com.quifers.email.helpers;

import com.quifers.domain.Order;
import com.quifers.email.util.Credentials;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class EmailSender {

    private static final String EMAIL_URL = "https://www.googleapis.com/gmail/v1/users/me/messages/send";

    private final EmailCreator emailCreator;

    public EmailSender(EmailCreator emailCreator) {
        this.emailCreator = emailCreator;
    }

    public int sendEmail(Credentials credentials, Order order, String fromAddress) throws IOException, MessagingException {
        HttpURLConnection urlConnection = getConnection(EMAIL_URL);
        urlConnection.addRequestProperty("Content-Type", "application/json");
        urlConnection.addRequestProperty("Authorization", "Bearer" + " " + credentials.getAccessToken());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MimeMessage mimeMessage = emailCreator.createEmail(order, fromAddress);
        mimeMessage.writeTo(out);
        byte[] binaryData = out.toByteArray();
        String encode = encodeBase64URLSafeString(binaryData);
        String format = "{\"raw\":\"" + encode + "\"}";
        return sendRequest(urlConnection, format);
    }

    private int sendRequest(HttpURLConnection connection, String request) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request);
        outputStream.flush();
        outputStream.close();
        return connection.getResponseCode();
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }

}
