package com.quifers.email.util;

import java.util.Date;

public class Credentials {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private int expiry;

    private Date grantTime;

    public Credentials(String accessToken, String refreshToken, String tokenType, int expiry, Date grantTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiry = expiry;
        this.grantTime = grantTime;
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

    public Date getGrantTime() {
        return grantTime;
    }
}
