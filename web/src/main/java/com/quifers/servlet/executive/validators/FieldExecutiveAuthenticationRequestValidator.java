package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.executive.FieldExecutiveAuthenticationRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.EmptyStringAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveAuthenticationRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final EmptyStringAttributeValidator emptyStringAttributeValidator;

    public FieldExecutiveAuthenticationRequestValidator(UserIdAttributeValidator userIdAttributeValidator, EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public FieldExecutiveAuthenticationRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId(userIdAttributeValidator.validate(getUserId(servletRequest)));
        String accessToken = emptyStringAttributeValidator.validate(getAccessToken(servletRequest));
        return new FieldExecutiveAuthenticationRequest(fieldExecutiveId, accessToken);
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }

    private String getAccessToken(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("access_token");
    }
}
