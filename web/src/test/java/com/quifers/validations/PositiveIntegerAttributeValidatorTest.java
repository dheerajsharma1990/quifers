package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PositiveIntegerAttributeValidatorTest {

    private final PositiveIntegerAttributeValidator validator = new PositiveIntegerAttributeValidator();

    @Test
    public void shouldThrowExceptionOnEmptyValue() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNullValue() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNegativeValue() {
        try {
            validator.validate("-256");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [-256] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNonDigitValue() {
        try {
            validator.validate("2i56");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [2i56] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldThrowExceptionOnOutOfRangeValues() {
        try {
            validator.validate("123456789123456789");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [123456789123456789] is out of range."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNumericValues() {
        try {
            validator.validate("14.54");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [14.54] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnInteger() throws InvalidRequestException {
        //when
        Integer value = validator.validate(" 092841 ");

        //then
        assertThat(value, is(92841));
    }
}
