package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class GeneratePriceRequest {

    private String orderId;

    public GeneratePriceRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        validate();
    }

    public String getOrderId() {
        return orderId;
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
    }
}
