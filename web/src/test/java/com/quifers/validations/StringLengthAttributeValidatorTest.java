package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringLengthAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final StringLengthAttributeValidator validator = new StringLengthAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionForLongName() {
        try {
            //given
            String longName = "Sometimes the name can be very long.But there has to be some limit.";
            when(emptyStringAttributeValidator.validate(longName)).thenReturn(longName);

            //when
            validator.validate(longName);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Name [Sometimes the name can be very long.But there has to be some limit.] is too long.Maximum length is [50]."));
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
