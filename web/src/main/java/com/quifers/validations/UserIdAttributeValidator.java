package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class UserIdAttributeValidator implements AttributeValidator<String> {

    private static final int MIN_USER_ID_LENGTH = 8;
    private static final int MAX_USER_ID_LENGTH = 30;

    @Override
    public String validate(String userId) throws InvalidRequestException {
        if (isEmpty(userId)) {
            throw new InvalidRequestException("UserId is empty.");
        }
        userId = userId.trim();
        if (userId.length() < MIN_USER_ID_LENGTH) {
            throw new InvalidRequestException("[" + userId + "] is too short.Minimum length is " + MIN_USER_ID_LENGTH + ".");
        }
        if (userId.length() > MAX_USER_ID_LENGTH) {
            throw new InvalidRequestException("[" + userId + "] is too long.Maximum length is " + MAX_USER_ID_LENGTH + ".");
        }
        return userId;
    }

}
