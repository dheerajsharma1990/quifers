package com.quifers.response;

import com.quifers.domain.Price;
import org.json.JSONObject;

public class PriceResponse {

    public static String getPriceResponse(Price price) {
        JSONObject object = new JSONObject();
        object.put("waitingCost", price.getWaitingCost());
        object.put("transitCost", price.getTransitCost());
        object.put("labourCost", price.getLabourCost());
        return object.toString();
    }

}
