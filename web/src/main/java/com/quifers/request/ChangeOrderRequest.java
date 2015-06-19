package com.quifers.request;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static com.quifers.domain.enums.OrderState.valueOf;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ChangeOrderRequest {

    private String orderId;
    private String orderState;

    public ChangeOrderRequest(HttpServletRequest request) throws InvalidRequestException {
        this.orderId = request.getParameter("order_id");
        this.orderState = request.getParameter("order_state");
        validate();
    }

    public OrderWorkflow getOrderWorkflow() {
        return new OrderWorkflow(new OrderId(orderId), valueOf(orderState), new Date());
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        if (isEmpty(orderState)) {
            throw new InvalidRequestException("Order State cannot be empty.");
        }
        try {
            valueOf(orderState.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidRequestException("Invalid Order State.");
        }
    }
}
