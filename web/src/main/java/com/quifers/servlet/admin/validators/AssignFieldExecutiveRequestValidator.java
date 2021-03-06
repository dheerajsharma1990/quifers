package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.request.admin.AssignFieldExecutiveRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.OrderIdAttributeValidator;
import com.quifers.validations.StringLengthAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class AssignFieldExecutiveRequestValidator implements RequestValidator {

    private final StringLengthAttributeValidator userIdAttributeValidator;
    private final OrderIdAttributeValidator orderIdAttributeValidator;

    public AssignFieldExecutiveRequestValidator(StringLengthAttributeValidator userIdAttributeValidator, OrderIdAttributeValidator orderIdAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.orderIdAttributeValidator = orderIdAttributeValidator;
    }

    @Override
    public AssignFieldExecutiveRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String fieldExecutiveId = userIdAttributeValidator.validate(getFieldExecutiveId(servletRequest));
        OrderId orderId = orderIdAttributeValidator.validate(getOrderId(servletRequest));
        return new AssignFieldExecutiveRequest(new FieldExecutiveId(fieldExecutiveId), orderId);
    }

    private String getOrderId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("order_id");
    }

    private String getFieldExecutiveId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("field_executive_id");
    }

}
