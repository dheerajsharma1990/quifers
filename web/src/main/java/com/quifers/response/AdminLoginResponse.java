package com.quifers.response;

import org.json.JSONObject;

public class AdminLoginResponse {

    public static String getSuccessResponse(String accessToken) {
        JSONObject object = new JSONObject();
        object.put("success", "true");
        object.put("access_token", accessToken);
        return object.toString();
    }

    public static String getInvalidLoginResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "false");
        return object.toString();
    }
}
