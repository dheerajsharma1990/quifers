package com.quifers.email.helpers;

import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;

import java.io.IOException;

public class CredentialsRefresher {

    private final HttpRequestSender httpRequestSender;
    private final AccessTokenRefreshRequestBuilder builder;
    private final JsonParser jsonParser;
    private final String refreshToken;

    public CredentialsRefresher(HttpRequestSender httpRequestSender, AccessTokenRefreshRequestBuilder builder, JsonParser jsonParser, String refreshToken) {
        this.httpRequestSender = httpRequestSender;
        this.builder = builder;
        this.jsonParser = jsonParser;
        this.refreshToken = refreshToken;
    }

    public Credentials getRefreshedCredentials() throws IOException {
        String request = builder.buildAccessTokenRefreshRequest(refreshToken);
        String response = httpRequestSender.sendRequestAndGetResponse("https://www.googleapis.com/oauth2/v3/token",request);
        return jsonParser.parseRefreshResponse(refreshToken, response);
    }

}
