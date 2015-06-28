package com.quifers.servlet.filters;

import com.quifers.authentication.Authenticator;
import com.quifers.servlet.ApiGroup;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.admin.validators.AdminAuthenticationRequestValidator;
import com.quifers.servlet.executive.validators.FieldExecutiveAuthenticationRequestValidator;
import com.quifers.servlet.validations.AccessTokenAttributeValidator;
import com.quifers.servlet.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationRequestHandlerFactory {

    public AuthenticationRequestHandler getAuthenticationRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup(servletRequest.getRequestURI());
        if (ApiGroup.ADMIN.equals(apiGroup)) {
            return new AdminAuthenticationRequestHandler(new AdminAuthenticationRequestValidator(new UserIdAttributeValidator(), new AccessTokenAttributeValidator()), new Authenticator());
        } else if (ApiGroup.FIELD_EXECUTIVE.equals(apiGroup)) {
            return new FieldExecutiveAuthenticationRequestHandler(new FieldExecutiveAuthenticationRequestValidator(), new Authenticator());
        } else {
            return new NoAuthenticationRequestHandler();
        }
    }
}