package com.quifers.validations;

public class StringLengthAttributeValidator implements AttributeValidator<String> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;
    private final int MAXIMUM_NAME_LENGTH = 50;

    public StringLengthAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public String validate(String name) throws InvalidRequestException {
        name = emptyStringAttributeValidator.validate(name);
        name = name.trim();
        if (name.length() > MAXIMUM_NAME_LENGTH) {
            throw new InvalidRequestException("Name [" + name + "] is too long.Maximum length is [" + MAXIMUM_NAME_LENGTH + "].");
        }
        return name;
    }
}
