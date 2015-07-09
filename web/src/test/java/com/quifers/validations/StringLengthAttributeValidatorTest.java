package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringLengthAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final StringLengthAttributeValidator validator = new StringLengthAttributeValidator(emptyStringAttributeValidator, 6, 15);

    @Test
    public void shouldThrowExceptionForShortValue() {
        try {
            //given
            String shortValue = "short";
            when(emptyStringAttributeValidator.validate(shortValue)).thenReturn(shortValue);

            //when
            validator.validate(shortValue);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [short] is too short.Minimum length is [6]."));
        }
    }

    @Test
    public void shouldThrowExceptionForLongValue() {
        try {
            //given
            String longName = "Sometimes the name can be very long.";
            when(emptyStringAttributeValidator.validate(longName)).thenReturn(longName);

            //when
            validator.validate(longName);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [Sometimes the name can be very long.] is too long.Maximum length is [15]."));
        }
    }

    @Test
    public void shouldPassValidationAndReturnName() throws InvalidRequestException {
        //given
        String givenName = "  Name Is Bond ";
        when(emptyStringAttributeValidator.validate(givenName)).thenReturn(givenName);

        //when
        String name = validator.validate(givenName);

        //then
        assertThat(name, is("Name Is Bond"));

    }
}
