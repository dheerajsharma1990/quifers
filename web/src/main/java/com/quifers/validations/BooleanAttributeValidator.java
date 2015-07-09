package com.quifers.validations;

public class BooleanAttributeValidator implements AttributeValidator<Boolean> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;

    public BooleanAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public Boolean validate(String value) throws InvalidRequestException {
        value = emptyStringAttributeValidator.validate(value);
        value = value.trim().toLowerCase();
        if (!("false".equals(value) || "true".equals(value))) {
            throw new InvalidRequestException("Value [" + value + "] should be either false or true.");
        }
        return Boolean.valueOf(value);
    }
}
