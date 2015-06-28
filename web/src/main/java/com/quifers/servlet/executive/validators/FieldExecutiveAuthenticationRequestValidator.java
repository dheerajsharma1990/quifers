package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.executive.FieldExecutiveAuthenticationRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.AccessTokenAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveAuthenticationRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final AccessTokenAttributeValidator accessTokenAttributeValidator;

    public FieldExecutiveAuthenticationRequestValidator(UserIdAttributeValidator userIdAttributeValidator, AccessTokenAttributeValidator accessTokenAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.accessTokenAttributeValidator = accessTokenAttributeValidator;
    }

    @Override
    public FieldExecutiveAuthenticationRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId(userIdAttributeValidator.validate(getUserId(servletRequest)));
        String accessToken = accessTokenAttributeValidator.validate(getAccessToken(servletRequest));
        return new FieldExecutiveAuthenticationRequest(fieldExecutiveId, accessToken);
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }

    private String getAccessToken(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("access_token");
    }
}
