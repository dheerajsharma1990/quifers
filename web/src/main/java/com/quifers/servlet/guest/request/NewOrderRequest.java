package com.quifers.servlet.guest.request;

import com.quifers.domain.Order;
import com.quifers.servlet.ApiRequest;

public class NewOrderRequest implements ApiRequest {

    private final Order order;

    public NewOrderRequest(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
