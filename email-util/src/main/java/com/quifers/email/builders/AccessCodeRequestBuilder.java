package com.quifers.email.builders;

import com.quifers.email.util.RequestParamBuilder;

import java.io.UnsupportedEncodingException;

public class AccessCodeRequestBuilder {

    public String buildAccessCodeRequest(String clientId, String redirectUri, String loginHint) throws UnsupportedEncodingException {
        RequestParamBuilder requestParamBuilder = new RequestParamBuilder();
        String requestParam = requestParamBuilder.addParam("response_type", "code")
                .addParam("client_id", clientId)
                .addParam("redirect_uri", redirectUri)
                .addParam("scope", "https://www.googleapis.com/auth/gmail.compose")
                .addParam("state", "FirstAccessCode")
                .addParam("access_type", "offline")
                .addParam("approval_prompt", "force")
                .addParam("login_hint", loginHint).build();
        return requestParam;
    }
}
