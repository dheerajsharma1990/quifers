package com.quifers.validations;

public class AttributeValidatorFactory {

    private static final EmptyStringAttributeValidator EMPTY_STRING_ATTRIBUTE_VALIDATOR = new EmptyStringAttributeValidator();
    private static final BooleanAttributeValidator BOOLEAN_ATTRIBUTE_VALIDATOR = new BooleanAttributeValidator(EMPTY_STRING_ATTRIBUTE_VALIDATOR);
    private static final DayAttributeValidator DAY_ATTRIBUTE_VALIDATOR = new DayAttributeValidator(EMPTY_STRING_ATTRIBUTE_VALIDATOR);
    private static final MobileNumberAttributeValidator MOBILE_NUMBER_ATTRIBUTE_VALIDATOR = new MobileNumberAttributeValidator(EMPTY_STRING_ATTRIBUTE_VALIDATOR);
    private static final StringLengthAttributeValidator STRING_LENGTH_ATTRIBUTE_VALIDATOR = new StringLengthAttributeValidator(EMPTY_STRING_ATTRIBUTE_VALIDATOR);

    public static EmptyStringAttributeValidator getEmptyStringAttributeValidator() {
        return EMPTY_STRING_ATTRIBUTE_VALIDATOR;
    }

    public static BooleanAttributeValidator getBooleanAttributeValidator() {
        return BOOLEAN_ATTRIBUTE_VALIDATOR;
    }

    public static DayAttributeValidator getDayAttributeValidator() {
        return DAY_ATTRIBUTE_VALIDATOR;
    }

    public static MobileNumberAttributeValidator getMobileNumberAttributeValidator() {
        return MOBILE_NUMBER_ATTRIBUTE_VALIDATOR;
    }

    public static StringLengthAttributeValidator getStringLengthAttributeValidator() {
        return STRING_LENGTH_ATTRIBUTE_VALIDATOR;
    }

}
