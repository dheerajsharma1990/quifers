package com.quifers.request;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FieldExecutiveAssignRequest {

    private String fieldExecutiveId;
    private String orderId;

    public FieldExecutiveAssignRequest(HttpServletRequest request) throws InvalidRequestException {
        this.fieldExecutiveId = request.getParameter("field_executive_id");
        this.orderId = request.getParameter("order_id");
        validate();
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return new FieldExecutiveId(fieldExecutiveId);
    }

    public OrderId getOrderId() {
        return new OrderId(orderId);
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(fieldExecutiveId)) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
    }
}
