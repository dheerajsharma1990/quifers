package com.quifers.request.admin;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.servlet.ApiRequest;

public class AssignFieldExecutiveRequest implements ApiRequest {

    private final OrderId orderId;

    private final FieldExecutiveId fieldExecutiveId;

    public AssignFieldExecutiveRequest(FieldExecutiveId fieldExecutiveId, OrderId orderId) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.orderId = orderId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }
}
