package com.quifers.servlet;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestHandler {

    void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException, InvalidRequestException;

}
