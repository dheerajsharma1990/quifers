package com.quifers.servlet.filters;

import com.quifers.authentication.Authenticator;
import com.quifers.servlet.ApiGroup;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.admin.validators.AdminAuthenticationRequestValidator;
import com.quifers.servlet.executive.validators.FieldExecutiveAuthenticationRequestValidator;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.validations.AttributeValidatorFactory.getEmptyStringAttributeValidator;
import static com.quifers.validations.AttributeValidatorFactory.getStringLengthAttributeValidator;

public class AuthenticationRequestHandlerFactory {

    public AuthenticationRequestHandler getAuthenticationRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup(servletRequest.getRequestURI());
        if (ApiGroup.ADMIN.equals(apiGroup)) {
            return new AdminAuthenticationRequestHandler(new AdminAuthenticationRequestValidator(getStringLengthAttributeValidator(8, 30), getEmptyStringAttributeValidator()), new Authenticator());
        } else if (ApiGroup.FIELD_EXECUTIVE.equals(apiGroup)) {
            return new FieldExecutiveAuthenticationRequestHandler(new FieldExecutiveAuthenticationRequestValidator(getStringLengthAttributeValidator(8, 30), getEmptyStringAttributeValidator()), new Authenticator());
        } else {
            return new NoAuthenticationRequestHandler();
        }
    }
}
