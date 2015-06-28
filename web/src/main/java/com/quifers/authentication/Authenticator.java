package com.quifers.authentication;

import com.quifers.validations.InvalidRequestException;
import com.quifers.request.admin.AdminAuthenticationRequest;
import com.quifers.request.executive.FieldExecutiveAuthenticationRequest;

import static com.quifers.authentication.AdminAuthenticationData.isValidAdminAccessToken;
import static com.quifers.authentication.AdminAuthenticationData.isValidFieldExecutiveAccessToken;

public class Authenticator {
    public boolean authenticateAdmin(AdminAuthenticationRequest adminAuthenticationRequest) throws InvalidRequestException {
        return isValidAdminAccessToken(adminAuthenticationRequest.getAdminId(), adminAuthenticationRequest.getAccessToken());
    }

    public boolean authenticateFieldExecutive(FieldExecutiveAuthenticationRequest fieldExecutiveAuthenticationRequest) throws InvalidRequestException {
        return isValidFieldExecutiveAccessToken(fieldExecutiveAuthenticationRequest.getFieldExecutiveId(), fieldExecutiveAuthenticationRequest.getAccessToken());
    }
}
