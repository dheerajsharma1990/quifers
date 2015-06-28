package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BooleanAttributeValidatorTest {

    private final BooleanAttributeValidator validator = new BooleanAttributeValidator();

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
    public void shouldThrowExceptionOnWrongValue() {
        try {
            validator.validate("Truess");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Value [truess] should be either false or true."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnBoolean() throws InvalidRequestException {
        //when
        Boolean value = validator.validate(" TrUE ");

        //then
        assertThat(value, is(true));
    }
}
