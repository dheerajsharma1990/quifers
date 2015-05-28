package com.quifers.request.validators;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.authentication.AdminAuthenticationData.isValidAdminAccessToken;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AuthenticationRequestValidator {

    public boolean validateAdmin(HttpServletRequest request) throws InvalidRequestException {
        String userId = validateAndGetUserId(request);
        String accessToken = validateAndGetAccessToken(request);
        return isValidAdminAccessToken(userId, accessToken);
    }

    public boolean validateFieldExecutve(HttpServletRequest request) throws InvalidRequestException {
        String userId = validateAndGetUserId(request);
        String accessToken = validateAndGetAccessToken(request);
        return isValidAdminAccessToken(userId, accessToken);
    }

    private String validateAndGetUserId(HttpServletRequest request) throws InvalidRequestException {
        String userId = request.getParameter("user_id");
        if (isEmpty(userId)) {
            throw new InvalidRequestException("User Id cannot be empty.");
        }
        return userId.trim();
    }

    private String validateAndGetAccessToken(HttpServletRequest request) throws InvalidRequestException {
        String accessToken = request.getParameter("accessToken");
        if (isEmpty(accessToken)) {
            throw new InvalidRequestException("Access Token cannot be empty.");
        }
        return accessToken.trim();
    }
}
