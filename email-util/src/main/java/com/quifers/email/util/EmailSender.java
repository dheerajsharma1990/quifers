package com.quifers.email.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;

public class EmailSender {

    private static final String EMAIL_URL = "https://www.googleapis.com/gmail/v1/users/me/messages/send";

    public String sendEmail(Credentials credentials, MimeMessage mimeMessage) throws IOException, MessagingException {
        HttpURLConnection urlConnection = getConnection(EMAIL_URL);
        urlConnection.addRequestProperty("Content-Type", "application/json");
        urlConnection.addRequestProperty("Authorization", "Bearer" + " " + credentials.getAccessToken());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mimeMessage.writeTo(out);
        byte[] binaryData = out.toByteArray();
        String encode = encodeBase64URLSafeString(binaryData);
        String format = "{\"raw\":\"" + encode + "\"}";
        sendRequest(urlConnection, format);
        return IOUtils.toString(urlConnection.getInputStream());
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

    public static void main(String[] args) throws IOException, MessagingException {
        JsonParser parser = new JsonParser();
        Credentials credentials = parser.parse(FileUtils.readFileToString(new File("./target/credentials.json")));
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("dheeraj.sharma.aws@gmail.com", "Team Quifers"));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress("dheerajsharma1990@gmail.com"));
        msg.setSubject("Thanks for placing Order");
        msg.setText("Hey Man Thanks for placing order...");
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail(credentials,msg);
    }

}
