package com.quifers.servlet.guest.handlers;

import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.authentication.AdminAuthenticator;
import com.quifers.domain.AdminAccount;
import com.quifers.response.AdminLoginResponse;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.request.AdminLoginRequest;
import com.quifers.servlet.guest.validators.AdminLoginRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginRequestHandler implements RequestHandler {

    private final AdminLoginRequestValidator requestValidator;
    private final AdminAuthenticator adminAuthenticator;

    public AdminLoginRequestHandler(AdminLoginRequestValidator requestValidator, AdminAuthenticator adminAuthenticator) {
        this.requestValidator = requestValidator;
        this.adminAuthenticator = adminAuthenticator;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        AdminLoginRequest adminLoginRequest = requestValidator.validateRequest(servletRequest);
        AdminAccount adminAccount = adminLoginRequest.getAdminAccount();
        servletResponse.setContentType("application/json");
        String loginResponse;
        if (!adminAuthenticator.isValidAdmin(adminAccount)) {
            loginResponse = AdminLoginResponse.getInvalidLoginResponse();
        } else {
            String accessToken = adminAccount.getAccessToken();
            AdminAuthenticationData.putAdminAccessToken(adminAccount.getAdminId(), accessToken);
            loginResponse = AdminLoginResponse.getSuccessResponse(accessToken);
        }
        servletResponse.getWriter().write(loginResponse);
    }
}
