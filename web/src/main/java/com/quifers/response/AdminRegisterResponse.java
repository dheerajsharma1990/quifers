package com.quifers.response;

import org.json.JSONObject;

public class AdminRegisterResponse {

    public static String getSuccessResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "true");
        return object.toString();
    }
}
