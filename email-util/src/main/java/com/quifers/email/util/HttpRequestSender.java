package com.quifers.email.util;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {

    public String sendRequestAndGetResponse(HttpURLConnection urlConnection, String request) throws IOException {
        urlConnection.setRequestMethod("POST");
        int responseCode = sendRequest(urlConnection, request);
        return parseResponse(urlConnection.getURL().toString(), request, urlConnection, responseCode);
    }

    public String sendRequestAndGetResponse(String url, String request) throws IOException {
        HttpURLConnection urlConnection = getConnection(url);
        int responseCode = sendRequest(urlConnection, request);
        return parseResponse(url, request, urlConnection, responseCode);
    }

    private String parseResponse(String url, String request, HttpURLConnection urlConnection, int responseCode) throws IOException {
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("An error occurred in sending request " + request + " to url " + url + "." +
                    getResponse(urlConnection.getErrorStream()));
        }
        return getResponse(urlConnection.getInputStream());
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
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
