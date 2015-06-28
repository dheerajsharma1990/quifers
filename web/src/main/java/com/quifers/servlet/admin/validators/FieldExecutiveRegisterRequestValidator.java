package com.quifers.servlet.admin.validators;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.admin.request.FieldExecutiveRegisterRequest;
import com.quifers.servlet.validations.*;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveRegisterRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final PasswordAttributeValidator passwordAttributeValidator;
    private final NameAttributeValidator nameAttributeValidator;
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator;

    public FieldExecutiveRegisterRequestValidator(UserIdAttributeValidator userIdAttributeValidator, PasswordAttributeValidator passwordAttributeValidator,
                                                  NameAttributeValidator nameAttributeValidator, MobileNumberAttributeValidator mobileNumberAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.passwordAttributeValidator = passwordAttributeValidator;
        this.nameAttributeValidator = nameAttributeValidator;
        this.mobileNumberAttributeValidator = mobileNumberAttributeValidator;
    }

    @Override
    public FieldExecutiveRegisterRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId(userIdAttributeValidator.validate(getFieldExecutiveId(servletRequest)));
        FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(fieldExecutiveId, passwordAttributeValidator.validate(getPassword(servletRequest)));
        FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveId, nameAttributeValidator.validate(getName(servletRequest)),
                mobileNumberAttributeValidator.validate(getMobileNumber(servletRequest)));
        return new FieldExecutiveRegisterRequest(fieldExecutiveAccount,fieldExecutive);
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

    private String getFieldExecutiveId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("field_executive_id");
    }

}
