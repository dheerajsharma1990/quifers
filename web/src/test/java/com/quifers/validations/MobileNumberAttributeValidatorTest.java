package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MobileNumberAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);
    private final MobileNumberAttributeValidator validator = new MobileNumberAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionForInvalidLengthMobileNumber() {
        try {
            //given
            String invalidMobileNumber = " 981234567890  ";
            when(emptyStringAttributeValidator.validate(invalidMobileNumber)).thenReturn(invalidMobileNumber);

            //when
            validator.validate(invalidMobileNumber);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number [981234567890] contains [12] digits.It should have only [10] digits."));
        }
    }

    @Test
    public void shouldThrowExceptionForInvalidMobileNumber() {
        try {
            //given
            String invalidMobileNumber = " 98989898o0  ";
            when(emptyStringAttributeValidator.validate(invalidMobileNumber)).thenReturn(invalidMobileNumber);

            //when
            validator.validate(invalidMobileNumber);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number [98989898o0] should only contain digits."));
        }
    }

    @Test
    public void shouldPassValidationOfCorrectMobileNumber() throws InvalidRequestException {
        //given
        String validMobileNumber = " 9811981198  ";
        when(emptyStringAttributeValidator.validate(validMobileNumber)).thenReturn(validMobileNumber);

        //when
        Long mobileNumber = validator.validate(validMobileNumber);

        //then
        assertThat(mobileNumber, is(9811981198l));
    }
}
