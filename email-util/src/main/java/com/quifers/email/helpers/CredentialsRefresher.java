package com.quifers.email.helpers;

import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.JsonParser;
import com.quifers.email.util.RequestParamBuilder;
import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CredentialsRefresher {

    private final String REFRESH_URL = "https://www.googleapis.com/oauth2/v3/token";

    private final EmailUtilProperties properties;
    private final JsonParser jsonParser;

    public CredentialsRefresher(EmailUtilProperties properties, JsonParser jsonParser) {
        this.properties = properties;
        this.jsonParser = jsonParser;
    }

    public Credentials getRefreshedCredentials(Credentials oldCredentials) throws IOException {
        HttpURLConnection urlConnection = getConnection(REFRESH_URL);
        String requestParameters = getRequestParameters(oldCredentials.getRefreshToken());
        int responseCode = sendRequest(urlConnection, requestParameters);
        if (responseCode != 200) {
            throw new IOException(IOUtils.toString(urlConnection.getErrorStream()));
        }
        return jsonParser.parseRefreshResponse(oldCredentials.getRefreshToken(), IOUtils.toString(urlConnection.getInputStream()));
    }

    private String getRequestParameters(String refreshToken) throws UnsupportedEncodingException {
        RequestParamBuilder builder = new RequestParamBuilder();
        return builder.addParam("refresh_token", refreshToken)
                .addParam("client_id", properties.getClientId())
                .addParam("client_secret", properties.getClientSecretKey())
                .addParam("grant_type", "refresh_token").build();
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
