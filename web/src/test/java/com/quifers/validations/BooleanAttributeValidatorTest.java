package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);
    private final BooleanAttributeValidator validator = new BooleanAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionOnWrongValue() {
        try {
            //given
            String truess = "Truess";
            when(emptyStringAttributeValidator.validate(truess)).thenReturn(truess);

            //when
            validator.validate(truess);
            Assert.fail();
        } catch (InvalidRequestException e) {
            //then
            assertThat(e.getMessage(), is("Value [truess] should be either false or true."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnBoolean() throws InvalidRequestException {
        //given
        String trueValue = " TrUE ";
        when(emptyStringAttributeValidator.validate(trueValue)).thenReturn(trueValue);

        //when
        Boolean value = validator.validate(trueValue);

        //then
        assertThat(value, is(true));
    }
}
