package com.quifers.email.credentials.builders;

import com.quifers.email.util.RequestParamBuilder;

import java.io.UnsupportedEncodingException;

public class AccessTokenRequestBuilder {

    public String buildAccessTokenRequest(String accessCode, String redirectUrl, String clientId, String secretKey) throws UnsupportedEncodingException {
        RequestParamBuilder builder = new RequestParamBuilder();
        return builder.addParam("code", accessCode)
                .addParam("client_id", clientId)
                .addParam("client_secret", secretKey)
                .addParam("redirect_uri", redirectUrl)
                .addParam("grant_type", "authorization_code").build();
    }
}
