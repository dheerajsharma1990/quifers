package com.quifers.servlet.guest.validators;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.request.guest.FieldExecutiveLoginRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.PasswordAttributeValidator;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveLoginRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final PasswordAttributeValidator passwordAttributeValidator;

    public FieldExecutiveLoginRequestValidator(UserIdAttributeValidator userIdAttributeValidator, PasswordAttributeValidator passwordAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.passwordAttributeValidator = passwordAttributeValidator;
    }

    @Override
    public FieldExecutiveLoginRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(userIdAttributeValidator.validate(getUserId(servletRequest)),
                passwordAttributeValidator.validate(getPassword(servletRequest)));
        return new FieldExecutiveLoginRequest(fieldExecutiveAccount);
    }

    private String getPassword(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("password");
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }
}
