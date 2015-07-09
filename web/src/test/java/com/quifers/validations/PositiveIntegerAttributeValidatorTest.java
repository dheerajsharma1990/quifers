package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositiveIntegerAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);
    private final PositiveIntegerAttributeValidator validator = new PositiveIntegerAttributeValidator(emptyStringAttributeValidator);


    @Test
    public void shouldThrowExceptionOnNegativeValue() {
        try {
            //given
            String negativeValue ="-256";
            when(emptyStringAttributeValidator.validate(negativeValue)).thenReturn(negativeValue);

            //when
            validator.validate(negativeValue);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [-256] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNonDigitValue() {
        try {
            //given
            String nonDigitValue = "2i56";
            when(emptyStringAttributeValidator.validate(nonDigitValue)).thenReturn(nonDigitValue);

            //when
            validator.validate(nonDigitValue);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [2i56] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldThrowExceptionOnOutOfRangeValues() {
        try {
            //given
            String outOfRangeValue = "123456789123456789";
            when(emptyStringAttributeValidator.validate(outOfRangeValue)).thenReturn(outOfRangeValue);

            //when
            validator.validate(outOfRangeValue);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [123456789123456789] is out of range."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNumericValues() {
        try {
            //given
            String numericValue = "14.54";
            when(emptyStringAttributeValidator.validate(numericValue)).thenReturn(numericValue);

            //when
            validator.validate(numericValue);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [14.54] should be positive and contain all digits."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnInteger() throws InvalidRequestException {
        //given
        String integerValue = " 092841 ";
        when(emptyStringAttributeValidator.validate(integerValue)).thenReturn(integerValue);

        //when
        Integer value = validator.validate(" 092841 ");

        //then
        assertThat(value, is(92841));
    }
}
