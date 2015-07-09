package com.quifers.servlet.guest.validators;


import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.request.guest.AdminRegisterRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.*;

import javax.servlet.http.HttpServletRequest;

public class AdminRegisterRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final PasswordAttributeValidator passwordAttributeValidator;
    private final StringLengthAttributeValidator stringLengthAttributeValidator;
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator;

    public AdminRegisterRequestValidator(UserIdAttributeValidator userIdAttributeValidator, PasswordAttributeValidator passwordAttributeValidator,
                                         StringLengthAttributeValidator stringLengthAttributeValidator, MobileNumberAttributeValidator mobileNumberAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.passwordAttributeValidator = passwordAttributeValidator;
        this.stringLengthAttributeValidator = stringLengthAttributeValidator;
        this.mobileNumberAttributeValidator = mobileNumberAttributeValidator;
    }

    @Override
    public AdminRegisterRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String adminId = userIdAttributeValidator.validate(getUserId(servletRequest));
        AdminAccount adminAccount = new AdminAccount(adminId,
                passwordAttributeValidator.validate(getPassword(servletRequest)));
        Admin admin = new Admin(adminId, stringLengthAttributeValidator.validate(getName(servletRequest)), mobileNumberAttributeValidator.validate(getMobileNumber(servletRequest)));
        return new AdminRegisterRequest(adminAccount, admin);
    }

    private String getMobileNumber(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("mobile_number");
    }

    private String getName(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("name");
    }

    private String getPassword(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("password");
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }
}
