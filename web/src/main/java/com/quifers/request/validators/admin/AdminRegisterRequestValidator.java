package com.quifers.request.validators.admin;

import com.quifers.request.AdminRegisterRequest;
import com.quifers.request.validators.InvalidRequestException;

import static com.quifers.request.validators.admin.AdminValidator.*;

public class AdminRegisterRequestValidator {

    public static void validateAdminRegisterRequest(AdminRegisterRequest registerRequest) throws InvalidRequestException {
        validateUserId(registerRequest.getUserId());
        validatePassword(registerRequest.getPassword());
        validateName(registerRequest.getName());
        validateMobileNumber(registerRequest.getMobileNumber());
    }

}
