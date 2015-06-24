package com.quifers.servlet.admin.validators;

import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.ApiRequest;
import com.quifers.servlet.RequestValidator;

import javax.servlet.http.HttpServletRequest;

public class AssignedOrdersRequestValidator implements RequestValidator {
    @Override
    public ApiRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return null;
    }
}
