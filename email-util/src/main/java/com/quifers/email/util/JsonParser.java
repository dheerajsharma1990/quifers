package com.quifers.email.util;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;

public class JsonParser {
    public Credentials parse(String jsonResponse) {
        JSONTokener tokener = new JSONTokener(jsonResponse);
        JSONObject object = new JSONObject(tokener);
        String accessToken = (String) object.get("access_token");
        String refreshToken = (String) object.get("refresh_token");
        String tokenType = (String) object.get("token_type");
        int expiry = (int) object.get("expires_in");
        return new Credentials(accessToken, refreshToken, tokenType, expiry, new Date());
    }
}
