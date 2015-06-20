package com.quifers.request;

import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class GeneratePriceRequest {

    private String orderId;
    private String distance;
    private String waitingMinutes;

    public GeneratePriceRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        this.distance = request.getParameter("distance");
        this.waitingMinutes = request.getParameter("waiting_minutes");
        validate();
    }

    public OrderId getOrderId() {
        return new OrderId(orderId);
    }

    public int getDistance() {
        return Integer.valueOf(distance);
    }

    public int getWaitingMinutes() {
        return Integer.valueOf(waitingMinutes);
    }


    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        if (isEmpty(distance)) {
            throw new InvalidRequestException("Distance cannot be empty.");
        }
        if (isEmpty(waitingMinutes)) {
            throw new InvalidRequestException("Waiting minutes cannot be empty.");
        }
    }
}
