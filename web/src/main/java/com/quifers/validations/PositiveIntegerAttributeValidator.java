package com.quifers.validations;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PositiveIntegerAttributeValidator implements AttributeValidator<Integer> {

    @Override
    public Integer validate(String value) throws InvalidRequestException {
        if (isEmpty(value)) {
            throw new InvalidRequestException("Value is empty.");
        }
        value = value.trim();
        String digitsRegex = "[0-9]+";
        if (!value.matches(digitsRegex)) {
            throw new InvalidRequestException("Value [" + value + "] should be positive and contain all digits.");
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Value [" + value + "] is out of range.");
        }
    }
}
