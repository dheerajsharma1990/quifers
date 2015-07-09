package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class EmptyStringAttributeValidator implements AttributeValidator<String> {

    @Override
    public String validate(String value) throws InvalidRequestException {
        if (isBlank(value)) {
            throw new InvalidRequestException("Value is empty.");
        }
        return value;
    }
}
