package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.OrderId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.request.executive.ReceivableRequest;

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
        try {
            Integer integer = Integer.valueOf(receivables);
            if (integer < 0) {
                throw new InvalidRequestException("Receivables must be greater then or equal to 0.");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid value of receivables entered.");
        }
    }
}
