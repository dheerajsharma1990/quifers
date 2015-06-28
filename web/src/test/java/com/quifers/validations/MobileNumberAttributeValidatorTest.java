package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MobileNumberAttributeValidatorTest {

    private final MobileNumberAttributeValidator validator = new MobileNumberAttributeValidator();

    @Test
    public void shouldThrowExceptionForEmptyMobileNumber() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForNullMobileNumber() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForInvalidLengthMobileNumber() {
        try {
            validator.validate(" 981234567890  ");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number [981234567890] contains [12] digits.It should have only [10] digits."));
        }
    }

    @Test
    public void shouldThrowExceptionForInvalidMobileNumber() {
        try {
            validator.validate(" 98989898o0  ");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile Number [98989898o0] should only contain digits."));
        }
    }

    @Test
    public void shouldPassValidationOfCorrectMobileNumber() throws InvalidRequestException {
        //when
        Long mobileNumber = validator.validate(" 9811981198  ");

        //then
        assertThat(mobileNumber, is(9811981198l));
    }
}
