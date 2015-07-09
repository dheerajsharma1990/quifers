package com.quifers.validations;

import static java.lang.Long.valueOf;

public class MobileNumberAttributeValidator implements AttributeValidator<Long> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;
    private final int MOBILE_NUMBER_LENGTH = 10;

    public MobileNumberAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public Long validate(String mobileNumber) throws InvalidRequestException {
        mobileNumber = emptyStringAttributeValidator.validate(mobileNumber);
        mobileNumber = mobileNumber.trim();
        if (mobileNumber.length() != MOBILE_NUMBER_LENGTH) {
            throw new InvalidRequestException("Mobile Number [" + mobileNumber + "] contains [" + mobileNumber.length() + "] digits." +
                    "It should have only [" + MOBILE_NUMBER_LENGTH + "] digits.");
        }
        String digitsRegex = "[0-9]+";
        if (!mobileNumber.matches(digitsRegex)) {
            throw new InvalidRequestException("Mobile Number [" + mobileNumber + "] should only contain digits.");
        }
        return valueOf(mobileNumber);
    }
}
