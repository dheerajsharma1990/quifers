package com.quifers.servlet;

import com.quifers.validations.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

public interface RequestValidator {
    ApiRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException;
}
