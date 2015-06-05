package com.quifers.response;

import com.quifers.domain.Order;
import org.json.JSONObject;

public class PriceResponse {

    public static String getPriceResponse(Order order) {
        JSONObject object = new JSONObject();
        object.put("waitingCost", order.getWaitingCost());
        object.put("transitCost", order.getTransitCost());
        object.put("labourCost", order.getLabourCost());
        return object.toString();
    }

}
