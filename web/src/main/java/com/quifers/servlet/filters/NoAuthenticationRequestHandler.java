package com.quifers.servlet.filters;

import com.quifers.servlet.InvalidRequestException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoAuthenticationRequestHandler implements AuthenticationRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws InvalidRequestException, IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
