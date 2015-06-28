package com.quifers.servlet.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class NameAttributeValidator implements AttributeValidator<String> {

    private final int MAXIMUM_NAME_LENGTH = 50;

    @Override
    public String validate(String name) throws InvalidRequestException {
        if (isEmpty(name)) {
            throw new InvalidRequestException("Name is empty.");
        }
        name = name.trim();
        if (name.length() > MAXIMUM_NAME_LENGTH) {
            throw new InvalidRequestException("Name [" + name + "] is too long.Maximum length is [" + MAXIMUM_NAME_LENGTH + "].");
        }
        return name;
    }
}
