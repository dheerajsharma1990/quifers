package com.quifers.servlet;

import com.quifers.servlet.validations.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

public interface RequestValidator {
    ApiRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException;
}
