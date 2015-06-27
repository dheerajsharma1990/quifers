package com.quifers.servlet.executive.request;

import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.RequestValidator;

import javax.servlet.http.HttpServletRequest;

public class ReceivableRequestValidator implements RequestValidator {
    @Override
    public ReceivableRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String orderId = validateOrderId(servletRequest.getParameter("order_id"));
        int receivables = validateReceivables(servletRequest.getParameter("receivables"));
        return new ReceivableRequest(new OrderId(orderId), receivables);
    }

    private String validateOrderId(String orderId) throws InvalidRequestException {
        if (orderId == null || orderId.trim().equals("")) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        return orderId.trim();
    }

    private int validateReceivables(String receivables) throws InvalidRequestException {
        if (receivables == null || receivables.trim().equals("")) {
            throw new InvalidRequestException("Receivables cannot be empty.");
        }
        receivables = receivables.trim();
        String digitsRegex = "[0-9]+";
        if (!receivables.matches(digitsRegex)) {
            throw new InvalidRequestException("Receivables should only contain digits.");
        }
        return Integer.valueOf(receivables);
    }
}
