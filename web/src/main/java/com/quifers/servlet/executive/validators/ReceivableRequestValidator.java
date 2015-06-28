package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.OrderId;
import com.quifers.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.request.executive.ReceivableRequest;
import com.quifers.validations.OrderIdAttributeValidator;
import com.quifers.validations.PositiveIntegerAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class ReceivableRequestValidator implements RequestValidator {

    private final OrderIdAttributeValidator orderIdAttributeValidator;
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator;

    public ReceivableRequestValidator(OrderIdAttributeValidator orderIdAttributeValidator, PositiveIntegerAttributeValidator positiveIntegerAttributeValidator) {
        this.orderIdAttributeValidator = orderIdAttributeValidator;
        this.positiveIntegerAttributeValidator = positiveIntegerAttributeValidator;
    }

    @Override
    public ReceivableRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        OrderId orderId = orderIdAttributeValidator.validate(getOrderId(servletRequest));
        int receivables = positiveIntegerAttributeValidator.validate(getReceivables(servletRequest));
        return new ReceivableRequest(orderId, receivables);
    }

    private String getReceivables(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("receivables");
    }

    private String getOrderId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("order_id");
    }

}
