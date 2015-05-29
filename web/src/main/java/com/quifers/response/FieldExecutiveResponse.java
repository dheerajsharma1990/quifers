package com.quifers.response;

import org.json.JSONObject;

public class FieldExecutiveResponse {
    public static String getSuccessResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "true");
        return object.toString();
    }
}
