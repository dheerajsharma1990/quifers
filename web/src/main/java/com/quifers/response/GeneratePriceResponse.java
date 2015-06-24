package com.quifers.response;

import com.quifers.domain.Cost;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GeneratePriceResponse {

    private final HttpServletResponse response;

    public GeneratePriceResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void writeResponse(Cost cost) throws IOException {
        response.setContentType("application/json");
        JSONObject object = new JSONObject();
        object.put("waitingCost", cost.getWaitingCost());
        object.put("transitCost", cost.getTransitCost());
        object.put("labourCost", cost.getLabourCost());
        object.put("totalCost", cost.getTotalCost());
        response.getWriter().write(object.toString());
    }
}
