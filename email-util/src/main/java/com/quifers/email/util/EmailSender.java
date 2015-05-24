package com.quifers.email.util;

import org.apache.commons.io.IOUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import static com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class EmailSender {

    public static void main(String[] args) throws IOException, MessagingException {
        Credentials credentials = new Credentials("ya29.fQForTOlADJfWHA3jtbhAiJqSl77Pq6eY8eBsnByx70hod9hnZqflGO05L4kBIh6l3E_FvJPd2Ppmg",
                "1/VFdCg3FnPH3OYhYMsazSjImm2YD40blm2nYji0KaRMo",
                "Bearer", 3600, new Date());

        HttpURLConnection urlConnection = getConnection("https://www.googleapis.com/gmail/v1/users/me/messages/send");

        urlConnection.addRequestProperty("Content-Type", "application/json");
        urlConnection.addRequestProperty("Authorization", "Bearer" + " " + credentials.getAccessToken());
        MimeMessage mimeMessage = createEmail("dheerajsharma1990@gmail.com", "dheeraj.sharma.aws@gmail.com", "Rest Subject", "Rest Body");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mimeMessage.writeTo(out);
        byte[] binaryData = out.toByteArray();
        String encode = encodeBase64URLSafeString(binaryData);
        String format = "{\"raw\":\"" + encode + "\"}";
        sendRequest(urlConnection, format);
        String res = IOUtils.toString(urlConnection.getInputStream());
        System.out.println(res);
    }

    private static int sendRequest(HttpURLConnection connection, String request) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request);
        outputStream.flush();
        outputStream.close();
        return connection.getResponseCode();
    }

    private static HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }

    private static MimeMessage createEmail(String to, String from, String subject,
                                           String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }


}
