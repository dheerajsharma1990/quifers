package com.quifers.request.validators;

import com.quifers.request.FilterRequest;

import static com.quifers.authentication.AdminAuthenticationData.isValidAdminAccessToken;
import static com.quifers.authentication.AdminAuthenticationData.isValidFieldExecutiveAccessToken;

public class AuthenticationRequestValidator {

    public boolean validateAdmin(FilterRequest filterRequest) throws InvalidRequestException {
        return isValidAdminAccessToken(filterRequest.getUserId(), filterRequest.getAccessToken());
    }

    public boolean validateFieldExecutve(FilterRequest filterRequest) throws InvalidRequestException {
        return isValidFieldExecutiveAccessToken(filterRequest.getUserId(), filterRequest.getAccessToken());
    }

}
