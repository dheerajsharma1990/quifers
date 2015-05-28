package com.quifers.email.builders;

import org.json.JSONObject;

public class EmailRequestBuilder {

    public String buildEmailRequest(String encodedData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("raw", encodedData);
        return jsonObject.toString();
    }

}
