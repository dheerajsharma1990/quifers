package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class BooleanAttributeValidator implements AttributeValidator<Boolean> {
    @Override
    public Boolean validate(String value) throws InvalidRequestException {
        if (isEmpty(value)) {
            throw new InvalidRequestException("Value is empty.");
        }

        value = value.trim().toLowerCase();
        if (!("false".equals(value) || "true".equals(value))) {
            throw new InvalidRequestException("Value [" + value + "] should be either false or true.");
        }
        return Boolean.valueOf(value);
    }
}
