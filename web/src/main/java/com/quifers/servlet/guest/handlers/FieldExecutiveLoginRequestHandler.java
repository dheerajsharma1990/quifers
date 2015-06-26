package com.quifers.servlet.guest.handlers;

import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.response.AdminLoginResponse;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.request.FieldExecutiveLoginRequest;
import com.quifers.servlet.guest.validators.FieldExecutiveLoginRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FieldExecutiveLoginRequestHandler implements RequestHandler {

    private final FieldExecutiveLoginRequestValidator requestValidator;
    private final FieldExecutiveAuthenticator fieldExecutiveAuthenticator;

    public FieldExecutiveLoginRequestHandler(FieldExecutiveLoginRequestValidator requestValidator, FieldExecutiveAuthenticator fieldExecutiveAuthenticator) {
        this.requestValidator = requestValidator;
        this.fieldExecutiveAuthenticator = fieldExecutiveAuthenticator;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        FieldExecutiveLoginRequest fieldExecutiveLoginRequest = requestValidator.validateRequest(servletRequest);
        FieldExecutiveAccount fieldExecutiveAccount = fieldExecutiveLoginRequest.getFieldExecutiveAccount();
        String loginResponse;
        if (!fieldExecutiveAuthenticator.isValidFieldExecutive(fieldExecutiveAccount)) {
            loginResponse = FieldExecutiveResponse.getInvalidLoginResponse();
        } else {
            String accessToken = fieldExecutiveAccount.getAccessToken();
            AdminAuthenticationData.putFieldExecutiveToken(fieldExecutiveAccount.getFieldExecutiveId().getUserId(), accessToken);
            loginResponse = AdminLoginResponse.getSuccessResponse(accessToken);
        }
        servletResponse.getWriter().write(loginResponse);
    }
}
