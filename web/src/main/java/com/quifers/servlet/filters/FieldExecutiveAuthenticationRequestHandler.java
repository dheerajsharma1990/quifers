package com.quifers.servlet.filters;

import com.quifers.authentication.Authenticator;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.executive.request.FieldExecutiveAuthenticationRequest;
import com.quifers.servlet.executive.validators.FieldExecutiveAuthenticationRequestValidator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FieldExecutiveAuthenticationRequestHandler implements AuthenticationRequestHandler {

    private final FieldExecutiveAuthenticationRequestValidator requestValidator;
    private final Authenticator authenticator;

    public FieldExecutiveAuthenticationRequestHandler(FieldExecutiveAuthenticationRequestValidator requestValidator, Authenticator authenticator) {
        this.requestValidator = requestValidator;
        this.authenticator = authenticator;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws InvalidRequestException, IOException, ServletException {
        FieldExecutiveAuthenticationRequest fieldExecutiveAuthenticationRequest = requestValidator.validateRequest(servletRequest);
        boolean authenticated = authenticator.authenticateFieldExecutive(fieldExecutiveAuthenticationRequest);
        if (!authenticated) {
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials.");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
