package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NameAttributeValidatorTest {

    private final NameAttributeValidator validator = new NameAttributeValidator();

    @Test
    public void shouldThrowExceptionForEmptyName() {
        //when
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Name is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForNullName() {
        //when
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Name is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForLongName() {
        //when
        try {
            validator.validate("Sometimes the name can be very long.But there has to be some limit.");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Name [Sometimes the name can be very long.But there has to be some limit.] is too long.Maximum length is [50]."));
        }
    }

    @Test
    public void shouldPassValidationAndReturnName() throws InvalidRequestException {
        //given
        String givenName = "  Name Is Bond ";

        //when
        String name = validator.validate(givenName);

        //then
        assertThat(name, is("Name Is Bond"));

    }
}
