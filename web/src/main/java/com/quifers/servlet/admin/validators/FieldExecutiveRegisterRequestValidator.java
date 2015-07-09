package com.quifers.servlet.admin.validators;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.RequestValidator;
import com.quifers.request.admin.FieldExecutiveRegisterRequest;
import com.quifers.validations.*;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveRegisterRequestValidator implements RequestValidator {

    private final StringLengthAttributeValidator userIdAttributeValidator;
    private final StringLengthAttributeValidator passwordAttributeValidator;
    private final StringLengthAttributeValidator nameValidator;
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator;

    public FieldExecutiveRegisterRequestValidator(StringLengthAttributeValidator userIdAttributeValidator, StringLengthAttributeValidator passwordAttributeValidator,
                                                  StringLengthAttributeValidator nameValidator, MobileNumberAttributeValidator mobileNumberAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.passwordAttributeValidator = passwordAttributeValidator;
        this.nameValidator = nameValidator;
        this.mobileNumberAttributeValidator = mobileNumberAttributeValidator;
    }

    @Override
    public FieldExecutiveRegisterRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId(userIdAttributeValidator.validate(getFieldExecutiveId(servletRequest)));
        FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(fieldExecutiveId, passwordAttributeValidator.validate(getPassword(servletRequest)));
        FieldExecutive fieldExecutive = new FieldExecutive(fieldExecutiveId, nameValidator.validate(getName(servletRequest)),
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
