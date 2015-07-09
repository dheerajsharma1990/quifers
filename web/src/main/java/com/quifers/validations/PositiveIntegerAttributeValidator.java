package com.quifers.validations;

public class PositiveIntegerAttributeValidator implements AttributeValidator<Integer> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;

    public PositiveIntegerAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public Integer validate(String value) throws InvalidRequestException {
        value = emptyStringAttributeValidator.validate(value);
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
