package com.quifers.response;

import com.quifers.domain.Order;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Responses {

    public static String getOrderResponse(Collection<Order> orders) {
        Collection<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(new OrderResponse(order));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", orderResponses);
        return jsonObject.toString();
    }

    public static String getReceivableResponse(Order order) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "true");
        jsonObject.put("remaining_amount", order.getCost().getTotalCost() - order.getReceivables());
        return jsonObject.toString();
    }
}
