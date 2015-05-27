package com.quifers.email.helpers;

import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.util.Credentials;
import com.quifers.email.util.HttpRequestSender;
import com.quifers.email.util.JsonParser;

import java.io.IOException;

public class CredentialsRefresher {

    private final String REFRESH_URL = "https://www.googleapis.com/oauth2/v3/token";

    private final HttpRequestSender httpRequestSender;
    private final AccessTokenRefreshRequestBuilder builder;
    private final JsonParser jsonParser;

    public CredentialsRefresher(HttpRequestSender httpRequestSender, AccessTokenRefreshRequestBuilder builder, JsonParser jsonParser) {
        this.httpRequestSender = httpRequestSender;
        this.builder = builder;
        this.jsonParser = jsonParser;
    }

    public Credentials getRefreshedCredentials(Credentials oldCredentials) throws IOException {
        String request = builder.buildAccessTokenRefreshRequest(oldCredentials.getRefreshToken());
        String response = httpRequestSender.sendRequestAndGetResponse(REFRESH_URL, "POST", request);
        return jsonParser.parseRefreshResponse(oldCredentials.getRefreshToken(), response);
    }

}
