package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AdminFilterRequest {

    private String userId;
    private String accessToken;

    public AdminFilterRequest(HttpServletRequest request) throws InvalidRequestException {
        userId = request.getParameter("user_id");
        accessToken = request.getParameter("access_token");
        validate();
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void validate() throws InvalidRequestException {
        if (isEmpty(userId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
        if (isEmpty(accessToken)) {
            throw new InvalidRequestException("Access Token cannot be empty.");
        }
    }
}
