package com.quifers.servlet.guest.validators;


import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.guest.request.AdminRegisterRequest;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Long.valueOf;

public class AdminRegisterRequestValidator implements RequestValidator {

    @Override
    public AdminRegisterRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String userId = validateAndGetUserId(servletRequest.getParameter("user_id"));
        String password = validatePassword(servletRequest.getParameter("password"));
        String name = validateName(servletRequest.getParameter("name"));
        long mobileNumber = validateMobileNumber(servletRequest.getParameter("mobile_number"));
        return new AdminRegisterRequest(new AdminAccount(new AdminId(userId), password),
                new Admin(new AdminId(userId), name, mobileNumber));
    }

    private String validateAndGetUserId(String adminId) throws InvalidRequestException {
        if (adminId == null || adminId.trim().equals("")) {
            throw new InvalidRequestException("Admin Id cannot be empty.");
        }

        if (adminId.length() > 20) {
            throw new InvalidRequestException("Admin Id too long.Max characters allowed is 20");
        }
        return adminId.trim();
    }

    private String validatePassword(String password) throws InvalidRequestException {
        if (password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Password cannot be empty.");
        }

        if (password.length() > 15) {
            throw new InvalidRequestException("Password too long.Max characters allowed is 15");
        }

        if (password.length() < 8) {
            throw new InvalidRequestException("Password too short.min characters allowed is 8");
        }

        return password;
    }

    private String validateName(String name) throws InvalidRequestException {
        if (name == null || name.trim().equals("")) {
            throw new InvalidRequestException("Name cannot be empty.");
        }

        if (name.length() > 50) {
            throw new InvalidRequestException("Name too long.Max characters allowed is 50");
        }

        return name;
    }

    private long validateMobileNumber(String mobileNumber) throws InvalidRequestException {
        if (mobileNumber == null || mobileNumber.trim().equals("")) {
            throw new InvalidRequestException("Mobile Number cannot be empty.");
        }
        mobileNumber = mobileNumber.trim();
        if (mobileNumber.length() != 10) {
            throw new InvalidRequestException("Mobile number must be of 10 digits.");
        }
        String digitsRegex = "[0-9]+";
        if (!mobileNumber.matches(digitsRegex)) {
            throw new InvalidRequestException("Mobile number should only contain digits.");
        }
        return valueOf(mobileNumber);
    }
}
