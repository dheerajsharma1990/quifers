package com.quifers.response;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeOrderResponse {

    private final HttpServletResponse response;

    public ChangeOrderResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void writeResponse() throws IOException {
        response.setContentType("application/json");
        JSONObject object = new JSONObject();
        object.put("success", "true");
        response.getWriter().write(object.toString());
    }

}
