package com.quifers.email.servlet;

import com.quifers.email.util.RequestParamBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccessTokenRequestServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenRequestServlet.class);
    private static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = request.getParameter("error");

        if (error != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
            return;
        }

        String accessCode = request.getParameter("code");
        LOGGER.info("Access Code: {}", accessCode);
        String requestParams = getRequestParameters(accessCode);
        HttpURLConnection urlConnection = getConnection(ACCESS_TOKEN_URL);
        sendRequest(urlConnection, requestParams);
        String apiResponse = getResponse(urlConnection);

        LOGGER.info("Api Response: {}", apiResponse);

    }

    private String getResponse(HttpURLConnection urlConnection) throws IOException {
        return IOUtils.toString(urlConnection.getInputStream());
    }

    private String getRequestParameters(String accessCode) throws UnsupportedEncodingException {
        RequestParamBuilder builder = new RequestParamBuilder();
        return builder.addParam("code", accessCode)
                .addParam("client_id", AccessCodeRequestServlet.CLIENT_ID)
                .addParam("client_secret", AccessCodeRequestServlet.CLIENT_SECRET_KEY)
                .addParam("redirect_uri", "http://localhost:8008/callback")
                .addParam("grant_type", "authorization_code").build();
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
