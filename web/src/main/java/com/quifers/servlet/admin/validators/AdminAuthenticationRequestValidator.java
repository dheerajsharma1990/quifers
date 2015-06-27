package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.AdminId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.admin.request.AdminAuthenticationRequest;

import javax.servlet.http.HttpServletRequest;

public class AdminAuthenticationRequestValidator implements RequestValidator {

    @Override
    public AdminAuthenticationRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new AdminAuthenticationRequest(new AdminId(validateAndGetUserId(servletRequest.getParameter("user_id"))),
                validateAndGetAccessToken(servletRequest.getParameter("access_token")));
    }

    private String validateAndGetUserId(String adminId) throws InvalidRequestException {
        if (adminId == null || adminId.trim().equals("")) {
            throw new InvalidRequestException("Admin Id cannot be empty.");
        }
        return adminId.trim();
    }

    private String validateAndGetAccessToken(String accessToken) throws InvalidRequestException {
        if (accessToken == null || accessToken.trim().equals("")) {
            throw new InvalidRequestException("Access Token cannot be empty.");
        }
        return accessToken.trim();
    }

}
