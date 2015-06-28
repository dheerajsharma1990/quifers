package com.quifers.servlet.guest.validators;

import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.request.guest.AdminLoginRequest;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginRequestValidator implements RequestValidator {

    @Override
    public AdminLoginRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new AdminLoginRequest(new AdminAccount(new AdminId(validateAndGetUserId(servletRequest.getParameter("user_id"))),
                validatePassword(servletRequest.getParameter("password"))));
    }

    private String validateAndGetUserId(String adminId) throws InvalidRequestException {
        if (adminId == null || adminId.trim().equals("")) {
            throw new InvalidRequestException("Admin Id cannot be empty.");
        }
        return adminId.trim();
    }

    private String validatePassword(String password) throws InvalidRequestException {
        if (password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
        return password;
    }
}
