package com.quifers.request.validators.admin;

import com.quifers.request.validators.InvalidRequestException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AdminValidator {

    public static void validateUserId(String userId) throws InvalidRequestException {
        if (isEmpty(userId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
    }

    public static void validatePassword(String password) throws InvalidRequestException {
        if (isEmpty(password)) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
    }

    public static void validateName(String name) throws InvalidRequestException {
        if (isEmpty(name)) {
            throw new InvalidRequestException("Name cannot be empty.");
        }
    }

    public static void validateMobileNumber(String mobile) throws InvalidRequestException {
        if (isEmpty(mobile)) {
            throw new InvalidRequestException("Mobile number cannot be empty.");
        }
        mobile = mobile.trim();
        if (mobile.length() != 10) {
            throw new InvalidRequestException("Mobile number must have 10 digits.");
        }
        try {
            Long mobileNumber = Long.valueOf(mobile);
            if (!(mobileNumber > 0)) {
                throw new InvalidRequestException("Mobile number must contain all valid digits.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Mobile number must contain all valid digits.");
        }

    }
}
