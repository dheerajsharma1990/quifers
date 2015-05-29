package com.quifers.request.validators.admin;

import com.quifers.request.AdminLoginRequest;
import com.quifers.request.validators.InvalidRequestException;

import static com.quifers.request.validators.admin.AdminValidator.validatePassword;
import static com.quifers.request.validators.admin.AdminValidator.validateUserId;

public class AdminLoginRequestValidator {
    public static void validateAdminLoginRequest(AdminLoginRequest registerRequest) throws InvalidRequestException {
        validateUserId(registerRequest.getUserId());
        validatePassword(registerRequest.getPassword());
    }

}
