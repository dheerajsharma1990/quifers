package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Long.valueOf;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class GeneratePriceRequest {

    private String orderId;

    public GeneratePriceRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        validate();
    }

    public long getOrderId() {
        return valueOf(orderId);
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
    }
}
