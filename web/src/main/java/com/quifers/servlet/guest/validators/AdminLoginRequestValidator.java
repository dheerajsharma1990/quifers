package com.quifers.servlet.guest.validators;

import com.quifers.domain.AdminAccount;
import com.quifers.request.guest.AdminLoginRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.StringLengthAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginRequestValidator implements RequestValidator {

    private final StringLengthAttributeValidator userIdAttributeValidator;
    private final StringLengthAttributeValidator passwordAttributeValidator;

    public AdminLoginRequestValidator(StringLengthAttributeValidator userIdAttributeValidator, StringLengthAttributeValidator passwordAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.passwordAttributeValidator = passwordAttributeValidator;
    }

    @Override
    public AdminLoginRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        AdminAccount adminAccount = new AdminAccount(userIdAttributeValidator.validate(getUserId(servletRequest)),
                passwordAttributeValidator.validate(getPassword(servletRequest)));
        return new AdminLoginRequest(adminAccount);
    }

    private String getPassword(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("password");
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }

}
