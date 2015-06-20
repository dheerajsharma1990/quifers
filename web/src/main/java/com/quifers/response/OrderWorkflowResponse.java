package com.quifers.response;

import com.quifers.domain.OrderWorkflow;

import java.text.SimpleDateFormat;

public class OrderWorkflowResponse {

    private String orderState;

    private String effectiveTime;

    public OrderWorkflowResponse(OrderWorkflow orderWorkflow) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.orderState = orderWorkflow.getOrderWorkflowId().getOrderState().name();
        this.effectiveTime = format.format(orderWorkflow.getEffectiveTime());
    }

    public String getOrderState() {
        return orderState;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }
}

