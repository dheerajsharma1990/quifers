package com.quifers.servlet.filters;

import com.quifers.authentication.Authenticator;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.admin.request.AdminAuthenticationRequest;
import com.quifers.servlet.admin.validators.AdminAuthenticationRequestValidator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminAuthenticationRequestHandler implements AuthenticationRequestHandler {

    private final AdminAuthenticationRequestValidator requestValidator;
    private final Authenticator authenticator;

    public AdminAuthenticationRequestHandler(AdminAuthenticationRequestValidator requestValidator, Authenticator authenticator) {
        this.requestValidator = requestValidator;
        this.authenticator = authenticator;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws InvalidRequestException, IOException, ServletException {
        AdminAuthenticationRequest adminAuthenticationRequest = requestValidator.validateRequest(servletRequest);
        boolean authenticated = authenticator.authenticateAdmin(adminAuthenticationRequest);
        if (!authenticated) {
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials.");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
