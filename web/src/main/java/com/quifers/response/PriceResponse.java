package com.quifers.response;

import com.quifers.domain.Cost;
import org.json.JSONObject;

public class PriceResponse {

    public static String getPriceResponse(Cost cost) {
        JSONObject object = new JSONObject();
        object.put("waitingCost", cost.getWaitingCost());
        object.put("transitCost", cost.getTransitCost());
        object.put("labourCost", cost.getLabourCost());
        return object.toString();
    }

}
