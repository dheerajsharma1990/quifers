package com.quifers.servlet;

import javax.servlet.http.HttpServletRequest;

public interface RequestValidator {
    ApiRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException;
}
