package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class GeneratePriceRequest {

    private String orderId;
    private String distance;

    public GeneratePriceRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        this.distance = request.getParameter("distance");
        validate();
    }

    public String getOrderId() {
        return orderId;
    }

    public int getDistance() {
        return Integer.valueOf(distance);
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        if (isEmpty(distance)) {
            throw new InvalidRequestException("Distance cannot be empty.");
        }
    }
}
