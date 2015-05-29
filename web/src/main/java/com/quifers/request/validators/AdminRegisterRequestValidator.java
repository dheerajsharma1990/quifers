package com.quifers.request.validators;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AdminRegisterRequestValidator {

    public Admin validateAdminAccountRequest(HttpServletRequest request) throws InvalidRequestException {
        String userId = validateAndGetUserId(request);
        String password = validateAndGetPassword(request);
        String name = validateAndGetName(request);
        long mobileNumber = validateAndGetMobileNumber(request);
        return new Admin(new AdminAccount(userId, password), name, mobileNumber);
    }

    private String validateAndGetUserId(HttpServletRequest request) throws InvalidRequestException {
        String userId = request.getParameter("user_id");
        if (isEmpty(userId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
        return userId.trim();
    }

    private String validateAndGetPassword(HttpServletRequest request) throws InvalidRequestException {
        String password = request.getParameter("password");
        if (isEmpty(password)) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
        return password.trim();
    }


    private String validateAndGetName(HttpServletRequest request) throws InvalidRequestException {
        String name = request.getParameter("name");
        if (isEmpty(name)) {
            throw new InvalidRequestException("Name cannot be empty.");
        }
        return name.trim();
    }

    private long validateAndGetMobileNumber(HttpServletRequest request) throws InvalidRequestException {
        String mobile = request.getParameter("mobile_number");
        if (isEmpty(mobile)) {
            throw new InvalidRequestException("Mobile number cannot be empty.");
        }
        mobile = mobile.trim();
        if (mobile.length() != 10) {
            throw new InvalidRequestException("Mobile number must have 10 digits.");
        }
        try {
            Long mobileNumber = Long.valueOf(mobile);
            return mobileNumber;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Mobile number must contain all digits.");
        }
    }

}
