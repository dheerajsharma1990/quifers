package com.quifers.validations;

public class StringLengthAttributeValidator implements AttributeValidator<String> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;
    private final int minLength;
    private final int maxLength;

    public StringLengthAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator, int minLength, int maxLength) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public String validate(String value) throws InvalidRequestException {
        value = emptyStringAttributeValidator.validate(value);
        value = value.trim();
        if (value.length() < minLength) {
            throw new InvalidRequestException("Value [" + value + "] is too short.Minimum length is [" + minLength + "].");
        }
        if (value.length() > maxLength) {
            throw new InvalidRequestException("Value [" + value + "] is too long.Maximum length is [" + maxLength + "].");
        }
        return value;
    }
}
