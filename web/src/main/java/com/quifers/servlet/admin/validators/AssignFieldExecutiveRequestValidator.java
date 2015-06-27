package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.admin.request.AssignFieldExecutiveRequest;

import javax.servlet.http.HttpServletRequest;

public class AssignFieldExecutiveRequestValidator implements RequestValidator {

    @Override
    public AssignFieldExecutiveRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String fieldExecutiveId = validateFieldExecutiveId(servletRequest.getParameter("field_executive_id"));
        String orderId = validateOrderId(servletRequest.getParameter("order_id"));
        return new AssignFieldExecutiveRequest(new FieldExecutiveId(fieldExecutiveId), new OrderId(orderId));
    }

    private String validateOrderId(String orderId) throws InvalidRequestException {
        if (orderId == null || orderId.trim().equals("")) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        return orderId.trim();
    }

    private String validateFieldExecutiveId(String fieldExecutiveId) throws InvalidRequestException {
        if (fieldExecutiveId == null || fieldExecutiveId.trim().equals("")) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }
        return fieldExecutiveId.trim();
    }
}
