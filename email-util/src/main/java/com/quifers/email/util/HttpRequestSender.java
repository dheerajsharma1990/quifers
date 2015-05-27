package com.quifers.email.util;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {

    public String sendRequestAndGetResponse(String url, String method, String request) throws IOException {
        HttpURLConnection urlConnection = getConnection(url, method);
        int responseCode = sendRequest(urlConnection, request);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("An error occurred in sending " + method +
                    " request " + request +
                    " to url " + url + "." +
                    getResponse(urlConnection.getErrorStream()));
        }
        return getResponse(urlConnection.getInputStream());
    }

    private HttpURLConnection getConnection(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        return connection;
    }

    private int sendRequest(HttpURLConnection connection, String request) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(request);
        outputStream.flush();
        outputStream.close();
        return connection.getResponseCode();
    }

    private String getResponse(InputStream inputStream) throws IOException {
        return inputStream != null ? IOUtils.toString(inputStream) : "";
    }
}
