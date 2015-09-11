package com.quifers.email.builders;

import com.quifers.email.properties.EmailUtilProperties;
import com.quifers.email.util.RequestParamBuilder;

import java.io.UnsupportedEncodingException;

public class AccessTokenRefreshRequestBuilder {

    private final EmailUtilProperties properties;

    public AccessTokenRefreshRequestBuilder(EmailUtilProperties properties) {
        this.properties = properties;
    }

    public String buildAccessTokenRefreshRequest() throws UnsupportedEncodingException {
        RequestParamBuilder builder = new RequestParamBuilder();
        return builder.addParam("refresh_token", properties.getRefreshToken())
                .addParam("client_id", properties.getClientId())
                .addParam("client_secret", properties.getClientSecretKey())
                .addParam("grant_type", "refresh_token").build();
    }
}
