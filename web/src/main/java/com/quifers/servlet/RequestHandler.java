package com.quifers.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {

    void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception;

}
