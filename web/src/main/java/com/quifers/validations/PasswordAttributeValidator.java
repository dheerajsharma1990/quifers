package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PasswordAttributeValidator implements AttributeValidator<String> {

    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    private static final int MAXIMUM_PASSWORD_LENGTH = 20;

    @Override
    public String validate(String password) throws InvalidRequestException {
        if (isEmpty(password)) {
            throw new InvalidRequestException("Password is empty.");
        }
        if (password.contains(" ")) {
            throw new InvalidRequestException("Password contains spaces.");
        }
        /*if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidRequestException("Password is too short.Minimum length is [" + MINIMUM_PASSWORD_LENGTH + "].");
        }
        if (password.length() > MAXIMUM_PASSWORD_LENGTH) {
            throw new InvalidRequestException("Password is too long.Maximum length is [" + MAXIMUM_PASSWORD_LENGTH + "].");
        }*/
        return password;
    }
}
