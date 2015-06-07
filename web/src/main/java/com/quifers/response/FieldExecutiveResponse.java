package com.quifers.response;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import org.json.JSONObject;

import java.util.*;

public class FieldExecutiveResponse {

    public static String getSuccessResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "true");
        return object.toString();
    }

    public static String getInvalidLoginResponse() {
        JSONObject object = new JSONObject();
        object.put("success", "false");
        return object.toString();
    }

    public static String getAllFieldExecutivesResponse(Collection<FieldExecutive> fieldExecutives) {
        List<FieldExecutiveListAll> listAlls = new ArrayList<>();
        for (FieldExecutive fieldExecutive : fieldExecutives) {
            listAlls.add(new FieldExecutiveListAll(fieldExecutive.getAccount().getUserId(), fieldExecutive.getName(), fieldExecutive.getMobileNumber()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field_executives", listAlls);
        return jsonObject.toString();
    }

    public static String getOrderResponse(Collection<Order> orders) {
        Collection<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orders) {
            orderResponses.add(new OrderResponse(order));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", orderResponses);
        return jsonObject.toString();
    }
}
