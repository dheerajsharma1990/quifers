package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AccessTokenAttributeValidator implements AttributeValidator<String> {
    @Override
    public String validate( String accessToken) throws InvalidRequestException {
        if (isEmpty(accessToken)) {
            throw new InvalidRequestException("Access Token is empty.");
        }
        return accessToken;
    }
}
