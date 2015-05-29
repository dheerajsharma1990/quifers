package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class LoginRequest {

    private String userId;
    private String password;

    public LoginRequest(HttpServletRequest request) throws InvalidRequestException {
        this.userId = request.getParameter("user_id");
        this.password = request.getParameter("password");
        validate();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(userId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
        if (isEmpty(password)) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
    }
}
