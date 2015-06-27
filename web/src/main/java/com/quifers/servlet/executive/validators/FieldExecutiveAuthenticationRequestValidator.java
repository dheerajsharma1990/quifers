package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.executive.request.FieldExecutiveAuthenticationRequest;

import javax.servlet.http.HttpServletRequest;

public class FieldExecutiveAuthenticationRequestValidator implements RequestValidator {


    @Override
    public FieldExecutiveAuthenticationRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new FieldExecutiveAuthenticationRequest(new FieldExecutiveId(validateAndGetUserId(servletRequest.getParameter("user_id"))),
                validateAndGetAccessToken(servletRequest.getParameter("access_token")));
    }

    private String validateAndGetUserId(String fieldExecutiveId) throws InvalidRequestException {
        if (fieldExecutiveId == null || fieldExecutiveId.trim().equals("")) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }
        return fieldExecutiveId.trim();
    }

    private String validateAndGetAccessToken(String accessToken) throws InvalidRequestException {
        if (accessToken == null || accessToken.trim().equals("")) {
            throw new InvalidRequestException("Access Token cannot be empty.");
        }
        return accessToken.trim();
    }
}
