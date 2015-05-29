package com.quifers.request;

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

    public String getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public String getOrderId() {
        return orderId;
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