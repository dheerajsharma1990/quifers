package com.quifers.servlet.validations;

import static java.lang.Long.valueOf;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MobileNumberAttributeValidator implements AttributeValidator<Long> {

    private final int MOBILE_NUMBER_LENGTH = 10;

    @Override
    public Long validate(String mobileNumber) throws InvalidRequestException {
        if (isEmpty(mobileNumber)) {
            throw new InvalidRequestException("Mobile Number is empty.");
        }
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
