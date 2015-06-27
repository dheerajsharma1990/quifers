package com.quifers.servlet.guest.validators;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.guest.request.FieldExecutiveLoginRequest;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveLoginRequestValidator implements RequestValidator {

    @Override
    public FieldExecutiveLoginRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new FieldExecutiveLoginRequest(new FieldExecutiveAccount(new FieldExecutiveId(validateAndGetUserId(servletRequest.getParameter("user_id"))),
                validatePassword(servletRequest.getParameter("password"))));
    }

    private String validateAndGetUserId(String fieldExecutiveId) throws InvalidRequestException {
        if (fieldExecutiveId == null || fieldExecutiveId.trim().equals("")) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }
        return fieldExecutiveId.trim();
    }

    private String validatePassword(String password) throws InvalidRequestException {
        if (password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
        return password;
    }
}
