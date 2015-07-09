package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.AdminId;
import com.quifers.request.admin.AdminAuthenticationRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.EmptyStringAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class AdminAuthenticationRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final EmptyStringAttributeValidator emptyStringValidator;

    public AdminAuthenticationRequestValidator(UserIdAttributeValidator userIdAttributeValidator, EmptyStringAttributeValidator emptyStringValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.emptyStringValidator = emptyStringValidator;
    }

    @Override
    public AdminAuthenticationRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        AdminId adminId = new AdminId(userIdAttributeValidator.validate(getUserId(servletRequest)));
        String accessToken = emptyStringValidator.validate(getAccessToken(servletRequest));
        return new AdminAuthenticationRequest(adminId, accessToken);
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }

    private String getAccessToken(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("access_token");
    }

}
