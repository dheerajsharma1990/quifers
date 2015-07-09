package com.quifers.validations;

import com.quifers.domain.Day;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DayAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final DayAttributeValidator validator = new DayAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionForInvalidDayFormat() {
        try {
            //given
            String invalidDay = "26-06-2015";
            when(emptyStringAttributeValidator.validate(invalidDay)).thenReturn(invalidDay);

            //when
            validator.validate(invalidDay);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Invalid day [26-06-2015].Correct format is [dd/MM/yyyy]."));
        }
    }

    @Test
    public void shouldPassValidationForCorrectDayFormat() throws InvalidRequestException, ParseException {
        //when
        String validDay = "  25/07/2015 ";
        when(emptyStringAttributeValidator.validate(validDay)).thenReturn(validDay);
        Day day = validator.validate(validDay);

        //then
        assertThat(day, is(new Day("25/07/2015")));
    }

}
