package com.quifers.email.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Credentials {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private int expiry;

    public Credentials(String accessToken, String refreshToken, String tokenType, int expiry) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiry = expiry;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiry() {
        return expiry;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
