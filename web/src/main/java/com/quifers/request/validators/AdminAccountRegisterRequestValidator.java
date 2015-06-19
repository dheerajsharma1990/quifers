package com.quifers.request.validators;

import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AdminAccountRegisterRequestValidator {

    public AdminAccount validateAdminAccountRequest(HttpServletRequest request) throws InvalidRequestException {
        String userId = validateAndGetUserId(request);
        String password = validateAndGetPassword(request);
        return new AdminAccount(new AdminId(userId), password);
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

}
