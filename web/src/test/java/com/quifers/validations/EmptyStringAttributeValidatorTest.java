package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmptyStringAttributeValidatorTest {

    private final EmptyStringAttributeValidator validator = new EmptyStringAttributeValidator();

    @Test
    public void shouldThrowExceptionOnEmptyString() {
        try {
            validator.validate("  ");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNullString() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value is empty."));
        }
    }

    @Test
    public void shouldPassValidationForNonEmptyString() throws InvalidRequestException {
        //when
        String string = validator.validate("anyString");

        //then
        assertThat(string, is("anyString"));
    }
}
