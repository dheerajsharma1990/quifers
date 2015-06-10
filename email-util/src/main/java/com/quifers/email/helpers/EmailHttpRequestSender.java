package com.quifers.email.helpers;

import com.quifers.email.util.Credentials;
import com.quifers.email.util.HttpRequestSender;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmailHttpRequestSender {

    private static final String EMAIL_URL = "https://www.googleapis.com/gmail/v1/users/me/messages/send";
    private final HttpRequestSender requestSender;

    public EmailHttpRequestSender(HttpRequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public String sendEmailHttpRequest(Credentials credentials, String request) throws IOException {
        HttpURLConnection urlConnection = getConnection(EMAIL_URL);
        urlConnection.addRequestProperty("Content-Type", "application/json");
        urlConnection.addRequestProperty("Authorization", "Bearer" + " " + credentials.getAccessToken());
        return requestSender.sendRequestAndGetResponse(urlConnection, "POST", request);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }
}
