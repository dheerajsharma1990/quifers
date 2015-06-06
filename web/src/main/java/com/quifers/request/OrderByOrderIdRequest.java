package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class OrderByOrderIdRequest {

    private String orderId;

    public OrderByOrderIdRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        validate();
    }

    public String getOrderId() {
        return orderId;
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
    }

}
