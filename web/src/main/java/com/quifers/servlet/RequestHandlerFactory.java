package com.quifers.servlet;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandlerFactory {
    RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException;
}
